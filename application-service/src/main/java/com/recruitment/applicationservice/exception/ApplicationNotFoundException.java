package com.recruitment.applicationservice.exception;

public class ApplicationNotFoundException extends RuntimeException {
    
    public ApplicationNotFoundException(String message) {
        super(message);
    }
}