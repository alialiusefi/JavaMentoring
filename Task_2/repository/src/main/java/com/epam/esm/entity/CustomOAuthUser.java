package com.epam.esm.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

public abstract class CustomOAuthUser extends User implements OAuth2User {

    protected Map<String, Object> oAuth2attributes;

    public CustomOAuthUser(String username, String password,
                           Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public CustomOAuthUser(UserEntity userEntity) {
        super(userEntity.getUsername(), userEntity.getPassword(), userEntity.getAuthorityList());
    }

    @Override
    public Map<String, Object> getAttributes() {
        return oAuth2attributes;
    }

    @Override
    public String getName() {
        return this.getName();
    }

    public abstract String getSubject();

}
