package com.project.secondApp.services;

import com.project.secondApp.exceptions.MovieExceptions.MovieAlreadyExistsException;
import com.project.secondApp.exceptions.MovieExceptions.MovieNotFoundException;
import com.project.secondApp.models.Movie.Movie;
import com.project.secondApp.models.Movie.MovieDto;
import com.project.secondApp.models.Movie.Type;
import com.project.secondApp.repositories.ActorRepository;
import com.project.secondApp.repositories.MovieRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
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
        assertNotNull(foundMovies);

        /* Checks for at least one execution */
        verify(movieRepository).findAll();
    }

    @Test
    void getMovie() {
        Movie movie = new Movie();
        when(movieRepository.findByTitle(anyString())).thenReturn(movie);

        MovieDto foundMovie = service.getMovie("Some movie");
        assertNotNull(foundMovie);

        verify(movieRepository).findByTitle("Some movie");
    }

    @Test
    void addNewMovieFail() {
        MovieDto movie = new MovieDto();
        movie.setTitle("Some OLD title");
        movie.setRating(1D);
        movie.setType(Type.horror);
        movie.setActors(new ArrayList<>());

        Movie oldMovie = new Movie();
        when(movieRepository.findByTitle("Some OLD title")).thenReturn(oldMovie);
        MovieAlreadyExistsException movieAlreadyExistsException = assertThrows(MovieAlreadyExistsException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                service.addNewMovie(movie);
            }
        });

        /* Verify result */
        String expectedString = "Movie " +  oldMovie.getTitle() + " already exists.";
        assertEquals(expectedString, movieAlreadyExistsException.getMessage());
        verify(movieRepository).findByTitle("Some OLD title");
    }

    @Test
    void addNewMovieSuccess() {
        MovieDto movie = new MovieDto();
        movie.setTitle("Some NEW title");
        movie.setRating(1D);
        movie.setType(Type.horror);
        movie.setActors(new ArrayList<>());

        assertDoesNotThrow( () -> service.addNewMovie(movie));

        verify(movieRepository).findByTitle("Some NEW title");
    }

    @Test
    void deleteMovie() {
        Movie movie = new Movie();
        when(movieRepository.findByTitle(anyString())).thenReturn(movie);

        MovieDto foundMovie = service.deleteMovie("Some movie");
        assertNotNull(foundMovie);

        verify(movieRepository).findByTitle("Some movie");
    }

    @Test
    void updateMovieSuccess() {
        Movie oldMovie = new Movie();
        when(movieRepository.findByTitle("Some title")).thenReturn(oldMovie);

        MovieDto updatedMovie = new MovieDto();
        updatedMovie.setTitle("Some title");
        updatedMovie.setRating(1D);
        updatedMovie.setType(Type.horror);
        updatedMovie.setActors(new ArrayList<>());

        assertDoesNotThrow( () -> service.updateMovie(updatedMovie.getTitle(), updatedMovie));

        verify(movieRepository).findByTitle("Some title");
    }

    // TEST EXCEPTION
    @Test
    void throwMovieNotFoundException() {

        String title = "Some OLD title";
        MovieNotFoundException movieNotFoundException = assertThrows(MovieNotFoundException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                service.getMovie(title);
            }
        });

        /* Verify result */
        String expectedString = "Associated movie does not exist.";
        assertEquals(expectedString, movieNotFoundException.getMessage());
        verify(movieRepository).findByTitle("Some OLD title");
    }

}