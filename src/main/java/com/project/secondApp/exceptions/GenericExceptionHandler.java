package com.project.secondApp.exceptions;

import com.project.secondApp.exceptions.ActorExceptions.ActorAlreadyExistsException;
import com.project.secondApp.exceptions.ActorExceptions.ActorNotFoundException;
import com.project.secondApp.exceptions.ActorExceptions.IncorrectGenderFormatException;
import com.project.secondApp.exceptions.ActorExceptions.IncorrectNameFormatException;
import com.project.secondApp.exceptions.MovieExceptions.MovieAlreadyExistsException;
import com.project.secondApp.exceptions.MovieExceptions.MovieNotFoundException;
import com.project.secondApp.exceptions.MovieExceptions.RatingExceedsLimitException;
import com.project.secondApp.exceptions.MovieExceptions.UnknownMovieTypeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GenericExceptionHandler {

    @ExceptionHandler(value = FailedDatabaseException.class)
    public ResponseEntity failedDatabaseException(FailedDatabaseException failedDatabaseException) {
        return new ResponseEntity<String>(failedDatabaseException.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = ActorAlreadyExistsException.class)
    public ResponseEntity actorAlreadyExistsException(ActorAlreadyExistsException actorAlreadyExistsException) {
        return new ResponseEntity<String>(actorAlreadyExistsException.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = ActorNotFoundException.class)
    public ResponseEntity actorNotFoundException(ActorNotFoundException actorNotFoundException) {
        return new ResponseEntity<String>(actorNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = IncorrectGenderFormatException.class)
    public ResponseEntity incorrectGenderFormatException(IncorrectGenderFormatException incorrectGenderFormatException) {
        return new ResponseEntity<String>(incorrectGenderFormatException.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = IncorrectNameFormatException.class)
    public ResponseEntity incorrectNameFormatException(IncorrectNameFormatException incorrectNameFormatException) {
        return new ResponseEntity<String>(incorrectNameFormatException.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = MovieAlreadyExistsException.class)
    public ResponseEntity movieAlreadyExistsException(MovieAlreadyExistsException movieAlreadyExistsException) {
        return new ResponseEntity<String>(movieAlreadyExistsException.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = MovieNotFoundException.class)
    public ResponseEntity movieNotFoundException(MovieNotFoundException movieNotFoundException) {
        return new ResponseEntity<String>(movieNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = RatingExceedsLimitException.class)
    public ResponseEntity ratingExceedsLimitException(RatingExceedsLimitException ratingExceedsLimitException) {
        return new ResponseEntity<String>(ratingExceedsLimitException.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = UnknownMovieTypeException.class)
    public ResponseEntity unknownMovieTypeException(UnknownMovieTypeException unknownMovieTypeException) {
        return new ResponseEntity<String>(unknownMovieTypeException.getMessage(), HttpStatus.NOT_FOUND);
    }
}
