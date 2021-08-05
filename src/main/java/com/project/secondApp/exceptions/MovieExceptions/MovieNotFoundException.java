package com.project.secondApp.exceptions.MovieExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class MovieNotFoundException  extends RuntimeException {
    private String message;

    public MovieNotFoundException(String message) {
        super(message);
        this.message = message;
    }
}
