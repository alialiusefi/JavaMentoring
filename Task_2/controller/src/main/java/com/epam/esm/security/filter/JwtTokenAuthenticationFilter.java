package com.epam.esm.security.filter;

import com.epam.esm.security.exception.InvalidJwtAuthenticationException;
import com.epam.esm.security.provider.JwtTokenProvider;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtTokenAuthenticationFilter extends GenericFilterBean {

    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    public JwtTokenAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        String accessToken = jwtTokenProvider.resolveAccessToken((HttpServletRequest) req,
                (HttpServletResponse) response);
        String refreshToken = jwtTokenProvider.resolveRefreshToken((HttpServletRequest) req,
                (HttpServletResponse) response);

        if (accessToken != null && refreshToken != null) {
            Authentication auth = null;
            try {
                jwtTokenProvider.validateToken(accessToken);
                auth = jwtTokenProvider.getAuthentication(accessToken);
            } catch (InvalidJwtAuthenticationException e) {
                try {
                    jwtTokenProvider.validateToken(refreshToken);
                    Authentication newAuthentication = jwtTokenProvider.getAuthentication(refreshToken);
                    String newAccessToken = jwtTokenProvider.generateAccessToken((UserDetails)
                            newAuthentication.getPrincipal());
                    ((HttpServletResponse) response).addHeader("Authorization", "Bearer " + newAccessToken);
                    if (newAccessToken != null && jwtTokenProvider.validateToken(newAccessToken)) {
                        auth = jwtTokenProvider.getAuthentication(newAccessToken);
                    }
                } catch (JwtException | IllegalArgumentException q) {
                    throw new InvalidJwtAuthenticationException("Invalid Refresh Token");
                }
            }
            if (auth != null) {
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        filterChain.doFilter(req, response);
    }
}