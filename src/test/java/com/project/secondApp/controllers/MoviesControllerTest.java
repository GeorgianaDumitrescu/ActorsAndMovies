package com.project.secondApp.controllers;

import com.project.secondApp.models.Movie.MovieDto;
import com.project.secondApp.repositories.ActorRepository;
import com.project.secondApp.repositories.MovieRepository;
import com.project.secondApp.services.MovieService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import java.util.ArrayList;
import java.util.List;

import static com.project.secondApp.models.Movie.Type.horror;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MoviesControllerTest {

    // TOOLS
    @Mock
    MovieService movieService;
    @Mock
    private ActorRepository actorRepository;
    @Mock
    private MovieRepository movieRepository;
    @Mock
    BindingResult bindingResult;

    // TESTED CLASS
    @InjectMocks
    MoviesController moviesController;

    @Test
    void list() {

        //given
        MovieDto firstMovie = new MovieDto();
        firstMovie.setTitle("Title 1");
        firstMovie.setType(horror);
        firstMovie.setRating(1D);
        firstMovie.setActors(new ArrayList<>());

        List<MovieDto> allMovies = new ArrayList<>();
        allMovies.add(firstMovie);
        //given (?)
        given(movieService.getMovies()).willReturn(allMovies);

        //when
        ResponseEntity<List<MovieDto>> response = moviesController.list();

        //then
        assertEquals(response.getStatusCode().value(), 200);
    }

    @Test
    void getMovie() {

        //given
        MovieDto firstMovie = new MovieDto();
        firstMovie.setTitle("Title 2");
        firstMovie.setType(horror);
        firstMovie.setRating(1D);
        firstMovie.setActors(new ArrayList<>());
        //given (?)
        given(movieService.getMovie("Title 2")).willReturn(firstMovie);

        //when
        ResponseEntity<MovieDto> response = moviesController.getMovie("Title 2");

        //then
        assertEquals(response.getStatusCode().value(), 200);
    }

    @Test
    void addMovie() {

        //given
        MovieDto movie = new MovieDto();
        movie.setTitle("Title 3");
        movie.setType(horror);
        movie.setRating(1D);
        movie.setActors(new ArrayList<>());
        //given (?)

        MovieDto newMovie = new MovieDto();
        given(movieService.addNewMovie(movie)).willReturn(newMovie);

        //when
        ResponseEntity<MovieDto> response = moviesController.create(movie);

        //then
        assertEquals(response.getStatusCode().value(), 200);
    }

}