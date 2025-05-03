package com.recruitment.userservice.service.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
class ForbiddenException extends RuntimeException {
    public ForbiddenException(String message) {
        super(message);
    }
}