package com.epam.esm.entity;

public enum Authority {

    ADMIN(1), USER(2), GUEST(3);

    private Integer value;

    Authority(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
