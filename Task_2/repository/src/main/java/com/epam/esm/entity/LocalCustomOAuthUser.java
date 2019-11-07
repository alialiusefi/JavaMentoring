package com.epam.esm.entity;

import java.util.List;

public class LocalCustomOAuthUser extends CustomOAuthUser {

    private String username;
    private List<Authority> authorityList;

    public LocalCustomOAuthUser(String username, String password,
                                List<Authority> authorities) {
        super(username, password, authorities);
        this.username = username;
        this.authorityList = authorities;
    }

    public LocalCustomOAuthUser(UserEntity userEntity) {
        super(userEntity);
    }

    @Override
    public String getSubject() {
        return this.username;
    }

    public List<Authority> getAuthorityList() {
        return authorityList;
    }
}
