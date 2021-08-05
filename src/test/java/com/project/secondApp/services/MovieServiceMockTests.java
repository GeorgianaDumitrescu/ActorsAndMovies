package com.project.secondApp.services;

import com.project.secondApp.exceptions.MovieExceptions.MovieNotFoundException;
import com.project.secondApp.models.Movie.Movie;
import com.project.secondApp.models.Movie.MovieDto;
import com.project.secondApp.repositories.ActorRepository;
import com.project.secondApp.repositories.MovieRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MovieServiceMockTests {

    @Mock
    private ActorRepository actorRepository;
    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    MovieService service;

    // TEST SERVICE METHODS
    @Test
    void getMovies() {
        List<MovieDto> movies = new ArrayList<>();
        when(service.getMovies()).thenReturn(movies);

        List<MovieDto> foundMovies = service.getMovies();
        assertFalse(foundMovies == null);

        /* Checks for at least one execution */
        verify(movieRepository).findAll();
    }

    @Test
    void getMovie() {
        Movie movie = new Movie();
        when(movieRepository.findByTitle(anyString())).thenReturn(movie);

        MovieDto foundMovie = service.getMovie("Some movie");
        assertFalse(foundMovie == null);

        verify(movieRepository).findByTitle("Some movie");
    }

    @Test
    void addNewMovie() {
    }

    @Test
    void deleteMovie() {
        Movie movie = new Movie();
        when(movieRepository.findByTitle(anyString())).thenReturn(movie);

        MovieDto foundMovie = service.deleteMovie("Some movie");
        assertFalse(foundMovie == null);

        verify(movieRepository).findByTitle("Some movie");
    }

    @Test
    void updateMovie() {
    }

    // TEST EXCEPTION
    @Test
    void throwMovieNotFoundException() {
        given(movieRepository.findByTitle(anyString())).willThrow(new MovieNotFoundException(""));

        assertThrows(MovieNotFoundException.class, ()->service.getMovie(anyString()));

        then(movieRepository).should().findByTitle(anyString());
    }

}