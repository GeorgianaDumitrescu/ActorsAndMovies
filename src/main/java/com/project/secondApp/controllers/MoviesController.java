package com.project.secondApp.controllers;

import com.project.secondApp.models.Movie;
import com.project.secondApp.repositories.MovieRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v2/movies")
public class MoviesController {
    @Autowired
    private MovieRepository movieRepository;

    // LIST MOVIES
    @GetMapping
    public List<Movie> list(){
        return movieRepository.findAll();
    }

    // ADD MOVIE
    @PostMapping
    public Movie create(@RequestBody final Movie movie){
        return movieRepository.saveAndFlush(movie);
    }

    // DELETE MOVIE
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id) {
        movieRepository.deleteById(id);
    }

    // UPDATE MOVIE
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public Movie update(@PathVariable Long id, @RequestBody Movie movie) {
        Movie existingMovie = movieRepository.getOne(id);
        BeanUtils.copyProperties(movie, existingMovie, "movie_id");
        return movieRepository.saveAndFlush(existingMovie);
    }
}
