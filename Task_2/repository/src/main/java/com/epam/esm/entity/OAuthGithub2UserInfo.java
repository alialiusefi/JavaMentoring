package com.epam.esm.entity;

import java.util.Map;

public class OAuthGithub2UserInfo extends OAuth2UserInfo {

    public OAuthGithub2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getID() {
        return ((Integer) attributes.get("id")).toString();
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }
}
