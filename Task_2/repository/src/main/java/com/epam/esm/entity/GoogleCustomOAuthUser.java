package com.epam.esm.entity;

import java.util.List;
import java.util.Map;

public class GoogleCustomOAuthUser extends CustomOAuthUser {

    public GoogleCustomOAuthUser(Map<String, Object> attributes, List<Authority> authorities) {
        super((String) attributes.get("login"), "nopass", authorities);
        this.oAuth2attributes = attributes;
    }

    @Override
    public String getSubject() {
        return (String) this.oAuth2attributes.get("email");
    }
}
