package com.project.secondApp.controllers;

import com.project.secondApp.models.Actor;
import com.project.secondApp.models.Movie;
import com.project.secondApp.models.UserActor;
import com.project.secondApp.repositories.ActorRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/v2/actors")
public class ActorsController {

    public UserActor getUserFriendlyInfo(Actor sourceActor) {
        UserActor destinationActor = new UserActor();

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
    @Autowired
    private ActorRepository actorRepository;

    // LIST ACTORS
    @GetMapping
    public List<UserActor> list(){
        List<UserActor> userActors = new ArrayList<UserActor>();
        List<Actor> movies = actorRepository.findAll();

        for(Actor iterator : movies) {
            UserActor actor = getUserFriendlyInfo(iterator);
            userActors.add(actor);
        }

        return userActors;
    }

    // ADD ACTOR
    @PostMapping
    public UserActor create(@RequestBody final Actor actor){
        Actor oldActor = actorRepository.findByName(actor.getName());

        /* Add actor only if it does not exist already */
        if (oldActor == null)
            actorRepository.saveAndFlush(actor);

        /* Create user friendly version of actor */
        UserActor actorCopy = getUserFriendlyInfo(actor);

        return actorCopy;
    }

    // DELETE ACTOR
    @RequestMapping(value = "{name}", method = RequestMethod.DELETE)
    public void delete(@PathVariable String name) {
        Actor deletedActor;

        // Write PathVariable as it is, no quotes (in Postman)
        deletedActor = actorRepository.findByName(name);
        actorRepository.deleteById(deletedActor.getId());
    }

    // UPDATE ACTOR
    @RequestMapping(value = "{name}", method = RequestMethod.PUT)
    public UserActor update(@PathVariable String name, @RequestBody Actor actor) {

        Actor existingActor = actorRepository.findByName(name);

        /* Keep old id */
        BeanUtils.copyProperties(actor, existingActor, "id");

        /* Save updated actor with old id */
        actorRepository.saveAndFlush(existingActor);

        /* Create user friendly version of actor */
        UserActor actorCopy = getUserFriendlyInfo(existingActor);

        return actorCopy;
    }
}
