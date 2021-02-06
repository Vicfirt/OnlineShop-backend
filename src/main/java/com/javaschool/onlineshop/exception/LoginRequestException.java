package com.javaschool.onlineshop.exception;

import org.springframework.http.HttpStatus;

public class LoginRequestException extends RuntimeException {

    private final HttpStatus status;

    public LoginRequestException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
