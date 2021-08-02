package com.project.secondApp.models;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class RatingExceedsLimitException  extends RuntimeException {
    private String message;

    public RatingExceedsLimitException(String message) {
        super(message);
        this.message = message;
    }
}
