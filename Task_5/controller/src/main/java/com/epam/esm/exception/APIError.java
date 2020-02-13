package com.epam.esm.exception;


import java.util.Arrays;
import java.util.List;

public class APIError {

    private List<String> errors;

    public APIError(List<String> errors) {
        super();
        this.errors = errors;
    }

    public APIError(String error) {
        super();
        errors = Arrays.asList(error);
    }


    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
