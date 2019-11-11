package com.epam.esm.dto;

public class UserWithoutPassDTO extends UserDTO {

    @Override
    public String getPassword() {
        return null;
    }
}
