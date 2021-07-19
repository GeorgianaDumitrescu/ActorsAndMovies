package com.project.secondApp.repositories;

import com.project.secondApp.models.Actor;

import java.util.List;

public interface ActorRepository {

    List<Actor> listActors();
    Actor addActors(Actor actor);
    void deleteActor(String title);
    Actor getOne(Long id);
    Actor saveAndFlush(Actor existingActor);
}
