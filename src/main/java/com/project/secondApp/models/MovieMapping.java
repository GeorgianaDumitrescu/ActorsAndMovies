package com.project.secondApp.models;

import java.util.ArrayList;
import java.util.List;

public class MovieMapping {

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
}
