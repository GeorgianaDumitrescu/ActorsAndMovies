package com.project.secondApp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class FailedDatabaseException extends RuntimeException {
    private String message;

    public FailedDatabaseException(String message) {
        super(message);
        this.message = message;
    }
}
