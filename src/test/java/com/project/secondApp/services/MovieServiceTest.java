package com.project.secondApp.services;

import com.project.secondApp.repositories.ActorRepository;
import com.project.secondApp.repositories.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MovieServiceTest {

    private ActorRepository actorRepository;
    private MovieRepository movieRepository;
    MovieService service;

    @BeforeEach
    void setUp(){
        service = new MovieService(actorRepository, movieRepository);
    }

    @Test
    void getMovies() {
        // TO DO
    }

    @Test
    void getMovie() {
        // TO DO
    }

    @Test
    void addNewMovie() {
        // TO DO
    }

    @Test
    void deleteMovie() {
        // TO DO
    }

    @Test
    void updateMovie() {
        // TO DO
    }
}