package com.project.secondApp.repositories;

import com.project.secondApp.models.Actor;
import com.project.secondApp.models.Movie;

import java.util.List;

public interface ActorRepository {

    List<Actor> listActors();
    void deleteActor(String title);
    Actor addActors(Actor actor);
    Actor getOne(Long id);
    Actor saveAndFlush(Actor existingActor);
}
