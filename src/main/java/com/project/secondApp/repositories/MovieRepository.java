package com.project.secondApp.repositories;

import com.project.secondApp.models.Movie;

import java.util.List;

public interface MovieRepository {
    List<Movie> listMovies();
    Movie addMovie(Movie movie);
    void deleteMovie(String title);
    Movie getOne(Long id);
    Movie saveAndFlush(Movie existingSession);
}
