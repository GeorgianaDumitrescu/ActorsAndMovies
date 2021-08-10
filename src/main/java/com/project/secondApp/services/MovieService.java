package com.project.secondApp.services;

import com.project.secondApp.exceptions.ActorExceptions.ActorNotFoundException;
import com.project.secondApp.exceptions.MovieExceptions.MovieAlreadyExistsException;
import com.project.secondApp.exceptions.MovieExceptions.MovieNotFoundException;
import com.project.secondApp.exceptions.MovieExceptions.RatingExceedsLimitException;
import com.project.secondApp.models.Actor.Actor;
import com.project.secondApp.models.Movie.Movie;
import com.project.secondApp.models.Movie.MovieDto;
import com.project.secondApp.repositories.ActorRepository;
import com.project.secondApp.repositories.MovieRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class MovieService {

    private ActorRepository actorRepository;
    private MovieRepository movieRepository;

    @Autowired
    public MovieService(ActorRepository actorRepository, MovieRepository movieRepository) {
        this.actorRepository = actorRepository;
        this.movieRepository = movieRepository;
    }

    private MovieDto getMapping(Movie sourceMovie) {
        MovieDto destinationMovie = new MovieDto();

        destinationMovie.setTitle(sourceMovie.getTitle());
        destinationMovie.setType(sourceMovie.getType());
        destinationMovie.setRating(sourceMovie.getRating());
        destinationMovie.setActors(new ArrayList<>());

        /* Raw */
        if (sourceMovie.getActors() != null) {
            List<Actor> actors = sourceMovie.getActors();
            /* Raw -> User friendly */
            for (Actor currentActor : actors) {
                if (currentActor != null) {
                    destinationMovie.getActors().add(currentActor.getName());
                }
            }
        }

        return destinationMovie;
    }

    private Movie getRawData(MovieDto sourceMovie) {
        Movie movie = new Movie();
        movie.setTitle(sourceMovie.getTitle());
        movie.setRating(sourceMovie.getRating());
        movie.setType(sourceMovie.getType());
        movie.setActors(new ArrayList<>());

        List<String> actors = sourceMovie.getActors();

        for (String actor : actors) {
            Actor newActor = actorRepository.findByName(actor);
            if (newActor == null) {
                /* Actor does not exist */
                throw new ActorNotFoundException("Associated actor does not exist.");
            }
            movie.getActors().add(newActor);
        }

        return movie;
    }

    private void validateMovie(Movie movie) {

        if (movie.getRating() > 10) {
            /* Rating exceeds limit */
            throw new RatingExceedsLimitException("Movie rating exceeds limit (limit = 10).");
        }

        List<Actor> actors = movie.getActors();

        for (Actor actor : actors) {
            if (actorRepository.findByName(actor.getName()) == null) {
                /* Associated actor does not exist */
                throw new ActorNotFoundException("Associated actor does not exist.");
            }
        }

        /* Movie is valid */
    }

    // LIST MOVIES
    public List<MovieDto> getMovies() {

        List<MovieDto> movies = new ArrayList<MovieDto>();
        List<Movie> moviesList = movieRepository.findAll();

        for (Movie movie : moviesList) {
            movies.add(getMapping(movie));
        }

        return movies;
    }


    // GET ACTOR
    public MovieDto getMovie(String title) {
        Movie movie = movieRepository.findByTitle(title);

        if (movie == null) {
            /* Did not find requested movie */
            throw new MovieNotFoundException("Associated movie does not exist.");
        }

        MovieDto responseMovie = getMapping(movie);
        return responseMovie;
    }

    // ADD MOVIE
    public MovieDto addNewMovie(final MovieDto newMovie) {

        Movie movie = getRawData(newMovie);

        Movie oldMovie = movieRepository.findByTitle(movie.getTitle());
        if (oldMovie != null) {
            /* Movie already exists */
            throw new MovieAlreadyExistsException("Movie " +  oldMovie.getTitle() + " already exists.");
        }

        validateMovie(movie);

        /* Add actor */
        movieRepository.saveAndFlush(movie);
        return newMovie;
    }

    // DELETE MOVIE
    public MovieDto deleteMovie(String title){

        Movie deletedMovie;

        // Write PathVariable as it is, no quotes (in Postman)
        deletedMovie = movieRepository.findByTitle(title);
        if (deletedMovie == null) {
            throw new MovieNotFoundException("Associated movie does not exist.");
        }
        MovieDto responseMovie = getMapping(deletedMovie);
        movieRepository.deleteById(deletedMovie.getId());

        return responseMovie;
    }

    // UPDATE MOVIE
    public MovieDto updateMovie(String title, MovieDto updatedMovie){

        Movie movie = getRawData(updatedMovie);
        Movie existingMovie = movieRepository.findByTitle(title);
        if(existingMovie == null){
            /* Movie does not exist */
            throw new MovieNotFoundException("Associated movie does not exist.");
        }

        validateMovie(movie);

        /* Keep old id */
        BeanUtils.copyProperties(movie, existingMovie, "id");

        /* Save updated movie with old id */
        movieRepository.saveAndFlush(existingMovie);

        return getMapping(existingMovie);
    }
}
