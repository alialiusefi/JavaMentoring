package com.epam.esm.security.provider;

import com.epam.esm.properties.AppProperties;
import com.epam.esm.security.exception.InvalidJwtAuthenticationException;
import com.epam.esm.service.CustomUserService;
import io.jsonwebtoken.*;
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

    private CustomUserService customUserService;
    private AppProperties appProperties;
    private String secretKey;
    private Long tokenDuration;

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
    }


    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + tokenDuration * 1000))
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

    public String resolveToken(HttpServletRequest req,
                               HttpServletResponse response) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        } else {
            String token = req.getParameter("token");
            response.addHeader("Authorization", "Bearer " + token);
            return token;
        }
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