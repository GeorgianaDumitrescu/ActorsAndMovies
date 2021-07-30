package com.project.secondApp.models;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GenericExceptionHandler {

    @ExceptionHandler(value = FailedDatabaseException.class)
    public ResponseEntity failedDatabaseException(FailedDatabaseException failedDatabaseException) {
        return new ResponseEntity<String>("Database failed.", HttpStatus.NOT_FOUND);
    }

}
