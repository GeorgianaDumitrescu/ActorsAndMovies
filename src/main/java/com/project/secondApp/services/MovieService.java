package com.project.secondApp.services;

import com.project.secondApp.models.*;
import com.project.secondApp.repositories.ActorRepository;
import com.project.secondApp.repositories.MovieRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
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
        List<Actor> actors = sourceMovie.getActors();

        /* Raw -> User friendly */
        for (Actor currentActor : actors) {
            destinationMovie.getActors().add(currentActor.getName());
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
                throw new ActorNotFoundException("");
            }
            movie.getActors().add(newActor);
        }

        return movie;
    }

    private void validateMovie(Movie movie) {

        //TO DO : * Not working *
        if (movie.getTypeString() !=  "action" &&
                movie.getTypeString() !=  "comedy" &&
                movie.getTypeString() !=  "horror") {
            /* Wrong movie type */
            throw new UnknownMovieTypeException("");
        }

        if (movie.getRating() > 10) {
            /* Rating exceeds limit */
            throw new RatingExceedsLimitException("");
        }

        List<Actor> actors = movie.getActors();

        for (Actor actor : actors) {
            if ((movie == null) || (actorRepository.findByName(actor.getName()) == null)) {
                /* Associated actor does not exist */
                throw new ActorNotFoundException("");
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
    public MovieDto getMovie(@PathVariable String title) {
        Movie movie = movieRepository.findByTitle(title);

        if (movie == null) {
            // Make new exception
            throw new MovieNotFoundException("");
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
            throw new MovieAlreadyExistsException("");
        }
        // TO DO : Throw exception for wrong type (Problem : Cannot deserialize value of wrong type)
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
            throw new MovieNotFoundException("");
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
            throw new MovieNotFoundException("");
        }

        // TO DO : Throw exception for wrong type (Problem : Cannot deserialize value of wrong type)
        validateMovie(movie);

        /* Keep old id */
        BeanUtils.copyProperties(movie, existingMovie, "id");

        /* Save updated movie with old id */
        movieRepository.saveAndFlush(existingMovie);

        return getMapping(existingMovie);
    }
}
