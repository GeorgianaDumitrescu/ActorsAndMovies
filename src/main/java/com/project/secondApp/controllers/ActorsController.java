package com.project.secondApp.controllers;

import com.project.secondApp.models.Actor;
import com.project.secondApp.repositories.ActorRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v2/actors")
public class ActorsController {
    @Autowired
    private ActorRepository actorRepository;

    // LIST ACTORS
    @GetMapping
    public List<Actor> list(){
        return actorRepository.listActors();
    }

    // ADD ACTOR
    @PostMapping
    public Actor create(@RequestBody final Actor actor){
        return actorRepository.addActors(actor);
    }

    // DELETE ACTOR
    @RequestMapping(value = "{title}", method = RequestMethod.DELETE)
    public void delete(@PathVariable String title) {
        actorRepository.deleteActor(title);
    }

    // UPDATE ACTOR
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public Actor update(@PathVariable Long id, @RequestBody Actor actor) {
        Actor existingActor = actorRepository.getOne(id);
        BeanUtils.copyProperties(actor, existingActor, "actor_id");
        return actorRepository.saveAndFlush(existingActor);
    }
}
