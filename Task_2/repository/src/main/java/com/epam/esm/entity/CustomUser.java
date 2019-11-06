package com.epam.esm.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

public class CustomUser extends User implements OAuth2User {


    private Map<String, Object> attributes;


    public CustomUser(String username, String password,
                      Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public CustomUser(com.epam.esm.entity.User user) {
        super(user.getUsername(), user.getPassword(), user.getAuthorityList());
    }

    public CustomUser(com.epam.esm.entity.User user, Map<String, Object> attributes) {
        this(user.getUsername(), user.getPassword(), user.getAuthorityList());
        this.attributes = attributes;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getName() {
        return this.getName();
    }
}
