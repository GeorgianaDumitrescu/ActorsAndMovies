package com.project.secondApp.services;

import com.project.secondApp.models.Actor;
import com.project.secondApp.models.Movie;
import com.project.secondApp.models.MovieDto;
import java.util.ArrayList;
import java.util.List;

public class MovieService {

    public MovieDto getMapping(Movie sourceMovie) {
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

    public Movie getRawData(MovieDto sourceMovie) {
        Movie movie = new Movie();
        movie.setTitle(sourceMovie.getTitle());
        movie.setRating(sourceMovie.getRating());
        movie.setType(sourceMovie.getType());
        movie.setActors(new ArrayList<>());

        return movie;
    }

    public String buildResponse(MovieDto movie) {
        String response = "Name: " + movie.getTitle() + '\n';
        response += "Rating: " + movie.getRating() + '\n';
        response += "Type: " + movie.getType() + '\n';
        response += "Cast: " + '\n';
        List<String> actors = movie.getActors();

        for (String actor : actors) {
            response += '\t' + actor + '\n';
        }

        return response;
    }
}
