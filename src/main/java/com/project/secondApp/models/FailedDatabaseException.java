package com.project.secondApp.models;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class FailedDatabaseException extends RuntimeException {
    public FailedDatabaseException(String message) {
        super(message);
    }
}
