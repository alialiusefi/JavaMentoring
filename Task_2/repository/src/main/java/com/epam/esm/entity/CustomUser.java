package com.epam.esm.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class CustomUser extends User {

    public CustomUser(String username, String password,
                      Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public CustomUser(com.epam.esm.entity.User user) {
        super(user.getUsername(), user.getPassword(), user.getAuthorityList());
    }

}
