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

    @ExceptionHandler(value = ActorAlreadyExistsException.class)
    public ResponseEntity actorAlreadyExistsException(ActorAlreadyExistsException actorAlreadyExistsException) {
        return new ResponseEntity<String>("Actor already exists.", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = ActorNotFoundException.class)
    public ResponseEntity actorNotFoundException(ActorNotFoundException actorNotFoundException) {
        return new ResponseEntity<String>("Associated actor does not exist.", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = IncorrectGenderFormatException.class)
    public ResponseEntity incorrectGenderFormatException(IncorrectGenderFormatException incorrectGenderFormatException) {
        return new ResponseEntity<String>("Gender type not found or incorrect format.", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = IncorrectNameFormatException.class)
    public ResponseEntity incorrectNameFormatException(IncorrectNameFormatException incorrectNameFormatException) {
        return new ResponseEntity<String>("Incorrect name format.", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = MovieAlreadyExistsException.class)
    public ResponseEntity movieAlreadyExistsException(MovieAlreadyExistsException movieAlreadyExistsException) {
        return new ResponseEntity<String>("Movie already exists in database.", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = MovieNotFoundException.class)
    public ResponseEntity movieNotFoundException(MovieNotFoundException movieNotFoundException) {
        return new ResponseEntity<String>("Associated movie does not exist.", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = RatingExceedsLimitException.class)
    public ResponseEntity ratingExceedsLimitException(RatingExceedsLimitException ratingExceedsLimitException) {
        return new ResponseEntity<String>("Rating exceeds limit, where limit = 10.", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = UnknownMovieTypeException.class)
    public ResponseEntity unknownMovieTypeException(UnknownMovieTypeException unknownMovieTypeException) {
        return new ResponseEntity<String>("Movie type not recognized.", HttpStatus.NOT_FOUND);
    }
}
