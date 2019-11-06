package com.epam.esm.dto;

import java.util.Map;

public class OAuthUserInfoDTO extends DTO {

    private Map<String, Object> attributes;

    public OAuthUserInfoDTO(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public String getEmail() {
        attributes.get("email");
    }

}
