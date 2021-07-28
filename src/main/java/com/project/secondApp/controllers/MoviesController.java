package com.project.secondApp.controllers;

import com.project.secondApp.models.*;
import com.project.secondApp.repositories.ActorRepository;
import com.project.secondApp.repositories.MovieRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/v2/movies")
public class MoviesController {

    @Autowired
    private MovieRepository movieRepository;
    @Autowired //TO DO
    private ActorRepository actorRepository;
    MovieMapping movieMapping = new MovieMapping();

    public ResponseEntity<String> validateMovie(Movie movie) {
        Movie oldMovie = movieRepository.findByTitle(movie.getTitle());

        if (movie.getRating() > 10) {
            /* Rating exceeds limit */
            return new ResponseEntity<>("Movie rating exceeds limit.", HttpStatus.valueOf(400));
        }

        List<Actor> actors = movie.getActors();

        for(Actor actor : actors) {
            if (actorRepository.findByName(actor.getName()) == null) {
                /* Associated actor does not exist */
                return new ResponseEntity<>("Associated actor does not exist.", HttpStatus.valueOf(400));
            }
        }

        if(oldMovie != null){
            /* Movie already exists */
            return new ResponseEntity<>("Movie already exists.", HttpStatus.valueOf(409));
        }

        /* Validate actor */
        return new ResponseEntity<>("Added new movie: " + movie.getTitle(), HttpStatus.OK);
    }

    // LIST MOVIES
    @GetMapping
    public ResponseEntity<List<MovieDto>> list(){

        List<MovieDto> movies = new ArrayList<MovieDto>();
        List<Movie> moviesList = movieRepository.findAll();

        for(Movie movie : moviesList) {
            movies.add(movieMapping.getMapping(movie));
        }

        return new ResponseEntity<>(movies, HttpStatus.OK);
    }

    // ADD MOVIE
    @PostMapping
    public ResponseEntity<String> create(@RequestBody final MovieDto newMovie){

        Movie movie = movieMapping.getRawData(newMovie);

        // TO DO : Move to MovieMapping (null import ??)
        List<String> actors = newMovie.getActors();

        for(String actor : actors) {
            movie.getActors().add(actorRepository.findByName(actor));
        }

        ResponseEntity<String> result = validateMovie(movie);
        HttpStatus HttpStatusCode = result.getStatusCode();

        if (HttpStatusCode == HttpStatus.valueOf(400) || HttpStatusCode == HttpStatus.valueOf(409)){
            /* 400 = Something wrong with movie fields
             * 409 = Movie already exists
             */
            return result;
        }

        /* Add actor */
        movieRepository.saveAndFlush(movie);

        return result;
    }

    // DELETE MOVIE
    @RequestMapping(value = "{title}", method = RequestMethod.DELETE)
    public ResponseEntity<String> delete(@PathVariable String title) {

        Movie deletedMovie;

        // Write PathVariable as it is, no quotes (in Postman)
        deletedMovie = movieRepository.findByTitle(title);
        movieRepository.deleteById(deletedMovie.getId());

        return new ResponseEntity<>("Deleted movie: " + deletedMovie.getTitle(), HttpStatus.OK);
    }

    // UPDATE MOVIE
    @RequestMapping(value = "{title}", method = RequestMethod.PATCH)
    public ResponseEntity<String> update(@PathVariable String title, @RequestBody MovieDto updatedMovie) {

        Movie movie = movieMapping.getRawData(updatedMovie);

        // TO DO : Move to MovieMapping (null import ??)
        List<String> actors = updatedMovie.getActors();

        for(String actor : actors) {
            movie.getActors().add(actorRepository.findByName(actor));
        }

        Movie existingMovie = movieRepository.findByTitle(title);
        if(existingMovie == null){
            /* Actor exists */
            return new ResponseEntity<>("Movie does not exist.", HttpStatus.valueOf(400));
        }

        ResponseEntity<String> result = validateMovie(movie);
        HttpStatus HttpStatusCode = result.getStatusCode();

        if (HttpStatusCode == HttpStatus.valueOf(400)){
            /* Something wrong with movie fields */
            return result;
        }

        /* Keep old id */
        BeanUtils.copyProperties(movie, existingMovie, "id");

        /* Save updated movie with old id */
        movieRepository.saveAndFlush(existingMovie);

        return new ResponseEntity<>("Updated movie: " + movie.getTitle(), HttpStatus.OK);
    }
}
