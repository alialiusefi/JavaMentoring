package com.epam.esm.entity;

import javax.persistence.Entity;


public enum UserStatus {

    ADMIN("Administrator"), USER("User"), GUEST("Guest");

    private String value;

    UserStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }


}
