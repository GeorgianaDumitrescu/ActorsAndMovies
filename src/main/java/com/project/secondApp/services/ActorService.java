package com.project.secondApp.services;

import com.project.secondApp.models.Actor;
import com.project.secondApp.models.ActorDto;
import com.project.secondApp.models.Movie;
import java.util.ArrayList;
import java.util.List;

public class ActorService {

    public ActorDto getMapping(Actor sourceActor) {
        ActorDto destinationActor = new ActorDto();

        destinationActor.setName(sourceActor.getName());
        destinationActor.setGender(sourceActor.getGender());
        destinationActor.setMovies(new ArrayList<>());

        /* Raw */
        List<Movie> movies = sourceActor.getMovies();

        /* Raw -> User friendly (if there are any movies the actor/actress starred in)*/
       for (Movie currentMovie : movies) {
           destinationActor.getMovies().add(currentMovie.getTitle());
        }

        return destinationActor;
    }

    public Actor getRawData(ActorDto sourceActor) {
        Actor actor = new Actor();
        actor.setName(sourceActor.getName());
        actor.setGender(sourceActor.getGender());
        actor.setMovies(new ArrayList<>());

        return actor;
    }

    public String buildResponse(ActorDto actor) {
        String response = "Name: " + actor.getName() + '\n';
        response += "Gender: " + actor.getGender() + '\n';
        response += "Movies: " + '\n';
        List<String> movies = actor.getMovies();

        for (String movie : movies) {
            response += '\t' + movie + '\n';
        }

        return response;
    }
}
