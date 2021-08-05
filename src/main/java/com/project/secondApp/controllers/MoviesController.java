package com.project.secondApp.controllers;

import com.project.secondApp.exceptions.FailedDatabaseException;
import com.project.secondApp.models.Movie.MovieDto;
import com.project.secondApp.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("api/v2/movies")
public class MoviesController {

    @Autowired
    MovieService movieService;

    // LIST MOVIES
    @GetMapping
    public ResponseEntity<List<MovieDto>> list(){

        List<MovieDto> movies = movieService.getMovies();
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }

    // GET MOVIE
    @GetMapping
    @RequestMapping("{title}")
    public ResponseEntity<MovieDto> getMovie(@PathVariable String title) {

        MovieDto responseMovie = movieService.getMovie(title);
        return ResponseEntity.ok(responseMovie);
    }

    // ADD MOVIE
    @PostMapping
    public ResponseEntity<MovieDto> create(@RequestBody final MovieDto newMovie) {

        MovieDto movie = movieService.addNewMovie(newMovie);
        return ResponseEntity.ok(movie);
    }

    // DELETE MOVIE
    @RequestMapping(value = "{title}", method = RequestMethod.DELETE)
    public HttpStatus delete(@PathVariable String title) {

        /* Keeping non void return for future uses */
        MovieDto deletedMovie = movieService.deleteMovie(title);
        return HttpStatus.OK;
    }

    // UPDATE MOVIE
    @RequestMapping(value = "{title}", method = RequestMethod.PATCH)
    public ResponseEntity<MovieDto> update(@PathVariable String title, @RequestBody MovieDto updatedMovie)  throws FailedDatabaseException {

        MovieDto movie = movieService.updateMovie(title, updatedMovie);
        return ResponseEntity.ok(movie);
    }
}
