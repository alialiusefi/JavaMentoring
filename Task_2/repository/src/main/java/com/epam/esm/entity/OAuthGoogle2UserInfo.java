package com.epam.esm.entity;

import java.util.Map;

public class OAuthGoogle2UserInfo extends OAuth2UserInfo {

    public OAuthGoogle2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getID() {
        return ((Integer) attributes.get("sub")).toString();
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }
}
