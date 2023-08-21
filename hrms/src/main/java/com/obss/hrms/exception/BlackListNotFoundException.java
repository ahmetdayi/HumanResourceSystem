package com.obss.hrms.exception;

public class BlackListNotFoundException extends RuntimeException {
    public BlackListNotFoundException(String message) {
        super(message);
    }
}
