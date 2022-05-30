package com.sourav.assignment.librarymanagement.model;

import org.springframework.stereotype.Component;

@Component
public class ErrorResponse {

    private String error;
    private String code;

    public ErrorResponse() {
    }

    public ErrorResponse(String error, String code) {
        this.error = error;
        this.code = code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
