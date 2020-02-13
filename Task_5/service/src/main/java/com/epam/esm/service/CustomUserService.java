package com.epam.esm.service;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface CustomUserService extends UserDetailsService {

    UserDetails loadUserByUsername(String username);

}
