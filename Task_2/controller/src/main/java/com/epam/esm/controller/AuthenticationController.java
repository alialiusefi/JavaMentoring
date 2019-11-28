package com.epam.esm.controller;

import com.epam.esm.dto.TokenDTO;
import com.epam.esm.dto.UserDTO;
import com.epam.esm.security.provider.JwtTokenProvider;
import com.epam.esm.service.CustomUserService;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/v2/auth")
@Validated
public class AuthenticationController {

    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;
    private CustomUserService userDetailsService;
    private UserService userService;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider,
                                    CustomUserService userDetailsService, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> authenticate(@Valid @RequestBody UserDTO dto) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(dto.getUsername(),
                dto.getPassword());
        authenticationManager.authenticate(token);
        final UserDetails userDetails = userDetailsService.loadUserByUsername(dto.getUsername());
        final String accessToken = jwtTokenProvider.generateAccessToken(userDetails);
        final String refreshToken = jwtTokenProvider.generateRefreshToken(userDetails);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Set-Cookie", "accessToken=" + accessToken + " Max-Age=604800; Path=/; Secure; HttpOnly");
        headers.add("Set-Cookie", "refreshToken=" + refreshToken + " Max-Age=604800; Path=/; Secure; HttpOnly");
        return ResponseEntity.ok().headers(headers).body(new TokenDTO(accessToken, refreshToken));
    }

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public void signUp(@Valid @RequestBody UserDTO dto) {
        userService.add(dto);
    }


}
