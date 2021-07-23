package com.project.secondApp.controllers;

import com.project.secondApp.models.Actor;
import com.project.secondApp.models.Movie;
import com.project.secondApp.models.UserMovie;
import com.project.secondApp.repositories.MovieRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/v2/movies")
public class MoviesController {

    public UserMovie getUserFriendlyInfo(Movie sourceMovie) {
        UserMovie destinationMovie = new UserMovie();

        destinationMovie.setTitle(sourceMovie.getTitle());
        destinationMovie.setType(sourceMovie.getType());
        destinationMovie.setRating(sourceMovie.getRating());
        destinationMovie.setActors(new ArrayList<>());

        /* Raw */
        List<Actor> actors = sourceMovie.getActors();

        /* Raw -> User friendly */
        for (Actor currentActor : actors) {
           destinationMovie.getActors().add(currentActor.getName());
        }

        return destinationMovie;
    }

    @Autowired
    private MovieRepository movieRepository;

    // LIST MOVIES
    @GetMapping
    public List<UserMovie> list(){

        List<UserMovie> userMovies = new ArrayList<UserMovie>();
        List<Movie> movies = movieRepository.findAll();

        for(Movie iterator : movies) {
            UserMovie movie = getUserFriendlyInfo(iterator);
            userMovies.add(movie);
        }

        return userMovies;
    }

    // ADD MOVIE
    @PostMapping
    public UserMovie create(@RequestBody final Movie movie){

        Movie oldMovie = movieRepository.findByTitle(movie.getTitle());

        /* Add movie only if it does not exist already */
        if (oldMovie == null)
            movieRepository.saveAndFlush(movie);

        /* Create user friendly version of movie */
        UserMovie movieCopy = getUserFriendlyInfo(movie);

        return movieCopy;
    }

    // DELETE MOVIE
    @RequestMapping(value = "{title}", method = RequestMethod.DELETE)
    public void delete(@PathVariable String title) {

        /* Write PathVariable as it is, no quotes (in Postman) */
        Movie deletedMovie = movieRepository.findByTitle(title);
        movieRepository.deleteById(deletedMovie.getId());
    }

    // UPDATE MOVIE
    @RequestMapping(value = "{title}", method = RequestMethod.PUT)
    public UserMovie update(@PathVariable String title, @RequestBody Movie movie) {

        Movie existingMovie = movieRepository.findByTitle(title);

        /* Keep old id */
        BeanUtils.copyProperties(movie, existingMovie, "id");

        /* Save updated movie with old id */
        movieRepository.saveAndFlush(existingMovie);

        /* Create user friendly version of movie */
        UserMovie movieCopy = getUserFriendlyInfo(existingMovie);

        return movieCopy;
    }
}
