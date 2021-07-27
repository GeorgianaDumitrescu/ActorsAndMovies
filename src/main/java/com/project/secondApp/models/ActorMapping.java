package com.project.secondApp.models;

import java.util.ArrayList;
import java.util.List;

public class ActorMapping {

    public ActorDto getMapping(Actor sourceActor) {
        ActorDto destinationActor = new ActorDto();

        destinationActor.setName(sourceActor.getName());
        destinationActor.setGender(sourceActor.getGender());
        destinationActor.setMovies(new ArrayList<>());

        /* Raw */
        List<Movie> movies = sourceActor.getMovies();

        /* Raw -> User friendly (if there are any movies the actor/actress starred in)*/
        if (!(movies == null)) {
            for (Movie currentMovie : movies) {
                destinationActor.getMovies().add(currentMovie.getTitle());
            }
        }

        return destinationActor;
    }

}
