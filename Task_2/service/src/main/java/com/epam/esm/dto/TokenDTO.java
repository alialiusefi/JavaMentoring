package com.epam.esm.dto;

public class TokenDTO extends DTO {

    private String jwttoken;

    public TokenDTO(String jwttoken) {
        this.jwttoken = jwttoken;
    }

    public String getToken() {
        return this.jwttoken;
    }


}
