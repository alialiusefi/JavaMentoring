package com.epam.esm.security.filter;

import com.epam.esm.security.provider.JwtTokenProvider;
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
    public void setJwtTokenProvider(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public static final String LOCAL_SIGNUP_URI = "/api/v2/auth/signup";
    public static final String LOCAL_LOGIN_URI = "api/v2/auth/login";

    @Override
    public void doFilter(ServletRequest req, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        /*if(isLoginURL((HttpServletRequest) req) || isSignupURL((HttpServletRequest) req)){
            Authentication auth = ;
                    SecurityContextHolder.getContext().setAuthentication(auth);

        }*/
        String token = jwtTokenProvider.resolveAccessToken((HttpServletRequest) req,
                (HttpServletResponse) response);
        String refreshToken = jwtTokenProvider.resolveRefreshToken((HttpServletRequest) req,
                (HttpServletResponse) response);
        if (token != null && jwtTokenProvider.validateToken(token)
                && refreshToken != null && jwtTokenProvider.validateToken(refreshToken)) {
            Authentication auth = jwtTokenProvider.getAuthentication(token);
            if (auth != null) {
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        } else {
            if (refreshToken != null && jwtTokenProvider.validateToken(refreshToken)) {
                String newAccessToken = jwtTokenProvider.generateAccessToken((UserDetails) SecurityContextHolder
                        .getContext().getAuthentication().getPrincipal());
                ((HttpServletResponse) response).addHeader("Authorization", "Bearer " + newAccessToken);
                if (newAccessToken != null && jwtTokenProvider.validateToken(newAccessToken)) {
                    Authentication auth = jwtTokenProvider.getAuthentication(newAccessToken);
                    if (auth != null) {
                        SecurityContextHolder.getContext().setAuthentication(auth);
                    }
                }
            }
        }
        filterChain.doFilter(req, response);
    }

    private boolean isLoginURL(HttpServletRequest request) {
        String URL = request.getRequestURI();
        return URL.equals(LOCAL_LOGIN_URI);
    }

    private boolean isSignupURL(HttpServletRequest request) {
        String URL = request.getRequestURI();
        return URL.equals(LOCAL_SIGNUP_URI);
    }

}