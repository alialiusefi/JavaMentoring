package com.epam.esm.security.provider;

import com.epam.esm.properties.AppProperties;
import com.epam.esm.security.exception.InvalidJwtAuthenticationException;
import com.epam.esm.service.CustomUserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenProvider {

    public static final String REFRESH_HEADER = "Refresh";
    private static final String ACCESS_HEADER_KEY = "Authorization";
    private static final String ACCESS_HEADER_VALUEPREFIX = "Bearer ";
    private CustomUserService customUserService;
    private AppProperties appProperties;
    private String secretKey;
    private Long tokenDuration;
    private Long refreshTokenExpirationMsec;

    @Autowired
    public JwtTokenProvider(CustomUserService customUserService, AppProperties appProperties) {
        this.customUserService = customUserService;
        this.appProperties = appProperties;

    }

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(
                appProperties.getAuth().getTokenSecret().getBytes());
        tokenDuration = appProperties.getAuth().getTokenExpirationMsec();
        refreshTokenExpirationMsec = appProperties.getAuth().getRefreshTokenExpirationMsec();
    }

    public String generateAccessToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateAccessToken(claims, userDetails.getUsername());
    }

    public String generateRefreshToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateRefreshToken(claims, userDetails.getUsername());
    }

    private String doGenerateAccessToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + tokenDuration * 1000))
                .signWith(SignatureAlgorithm.HS512, secretKey).compact();

    }

    private String doGenerateRefreshToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpirationMsec * 1000))
                .signWith(SignatureAlgorithm.HS512, secretKey).compact();
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = this.customUserService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails,
                userDetails.getPassword(), userDetails.getAuthorities());
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveAccessToken(HttpServletRequest req,
                                     HttpServletResponse response) {
        String bearerToken = req.getHeader(ACCESS_HEADER_KEY);
        if (bearerToken != null && bearerToken.startsWith(ACCESS_HEADER_VALUEPREFIX)) {
            return bearerToken.substring(7);
        } else {
            String token = req.getParameter("token");
            if (token != null) {
                response.addHeader(ACCESS_HEADER_KEY, ACCESS_HEADER_VALUEPREFIX + token);
                return token;
            }
        }
        throw new InvalidJwtAuthenticationException("Cannot resolve access token!");

    }

    public String resolveRefreshToken(HttpServletRequest req, HttpServletResponse response) {
        String token = req.getHeader(REFRESH_HEADER);
        if (token != null) {
            return token;
        } else {
            token = req.getParameter("refresh");
            if (token != null) {
                if (!response.containsHeader(REFRESH_HEADER)) {
                    response.addHeader(REFRESH_HEADER, token);
                } else {
                    response.setHeader(REFRESH_HEADER, token);
                }
                return token;
            }
        }
        throw new InvalidJwtAuthenticationException("Cannot resolve refresh token!");
    }


    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            Date date = new Date();
            return !claims.getBody().getExpiration().before(date);
        } catch (JwtException | IllegalArgumentException e) {
            throw new InvalidJwtAuthenticationException("Expired or invalid JWT token");
        }
    }
}