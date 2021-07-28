package com.project.secondApp.controllers;

import com.project.secondApp.models.Actor;
import com.project.secondApp.models.ActorDto;
import com.project.secondApp.models.ActorMapping;
import com.project.secondApp.models.Movie;
import com.project.secondApp.repositories.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/v2/actors")
public class ActorsController {

    @Autowired
    private ActorRepository actorRepository;
    @Autowired // TO DO
    private MovieRepository movieRepository;
    ActorMapping actorMapping = new ActorMapping();

    public ResponseEntity<String> validateActor(Actor actor) {
        Actor oldActor = actorRepository.findByName(actor.getName());

        if (actor.getName().matches(".*\\d.*")) {
            /* Actor name has incorrect format (no numbers allowed)*/
            return new ResponseEntity<>("Actor name contains numbers.", HttpStatus.valueOf(400));
        }

        if (!(actor.getGender().equals("female") || actor.getGender().equals("male"))){
            /* Actor gender has incorrect type/format (no spaces allowed) */
            return new ResponseEntity<>("Actor gender not recognized.", HttpStatus.valueOf(400));
        }

        List<Movie> movies = actor.getMovies();

        for(Movie movie : movies) {
            if ((movie == null) || (movieRepository.findByTitle(movie.getTitle()) == null)) {
                /* Associated movie does not exist */
                return new ResponseEntity<>("Associated movie does not exist.", HttpStatus.valueOf(400));
            }
        }

        if(oldActor != null){
            /* Actor exists */
            return new ResponseEntity<>("Actor already exists.", HttpStatus.valueOf(409));
        }

        /* Validate actor */
        return new ResponseEntity<>("Added new actor: " + actor.getName(), HttpStatus.OK);
    }

    // LIST ACTORS
    @GetMapping
    public ResponseEntity<List<ActorDto>> list(){
        List<ActorDto> actors = new ArrayList<>();
        List<Actor> actorsList = actorRepository.findAll();

        for(Actor actor : actorsList) {
            actors.add(actorMapping.getMapping(actor));
        }

        return new ResponseEntity<>(actors, HttpStatus.OK);
    }

    // ADD ACTOR
    @PostMapping
    public ResponseEntity<String> create(@RequestBody final ActorDto newActor){

        Actor actor = actorMapping.getRawData(newActor);

        // TO DO : Move to ActorMapping (null import ??)
        List<String> movies = newActor.getMovies();

        for(String movie : movies) {
            actor.getMovies().add(movieRepository.findByTitle(movie));
        }

        ResponseEntity<String> result = validateActor(actor);
        HttpStatus HttpStatusCode = result.getStatusCode();

        if (HttpStatusCode == HttpStatus.valueOf(400) || HttpStatusCode == HttpStatus.valueOf(409)){
            /*
             * 400 = Something wrong with actor fields
             * 409 = Actor already exists
             */
            return result;
        }

        /* Add actor */
        actorRepository.saveAndFlush(actor);

        return result;
    }

    // DELETE ACTOR
    @RequestMapping(value = "{name}", method = RequestMethod.DELETE)
    public ResponseEntity<String> delete(@PathVariable String name) {
        Actor deletedActor;

        // Write PathVariable as it is, no quotes (in Postman)
        deletedActor = actorRepository.findByName(name);
        actorRepository.deleteById(deletedActor.getId());

        return new ResponseEntity<>("Deleted actor: " + deletedActor.getName(), HttpStatus.OK);
    }

    // UPDATE ACTOR
    @RequestMapping(value = "{name}", method = RequestMethod.PATCH)
    public ResponseEntity<String> update(@PathVariable String name, @RequestBody ActorDto updatedActor) {

        Actor actor = actorMapping.getRawData(updatedActor);

        // TO DO : Move to ActorMapping (null import ??)
        List<String> movies = updatedActor.getMovies();

        for(String movie : movies) {
            actor.getMovies().add(movieRepository.findByTitle(movie));
        }

        Actor existingActor = actorRepository.findByName(name);
        if(existingActor == null){
            /* Actor exists */
            return new ResponseEntity<>("Actor does not exist.", HttpStatus.valueOf(400));
        }

        ResponseEntity<String> result = validateActor(actor);
        HttpStatus HttpStatusCode = result.getStatusCode();

        if (HttpStatusCode == HttpStatus.valueOf(400)){
            /* Something wrong with actor fields */
            return result;
        }

        /* Keep old id */
        BeanUtils.copyProperties(actor, existingActor, "id");

        /* Save updated actor with old id */
        actorRepository.saveAndFlush(existingActor);

        return new ResponseEntity<>("Updated actor: " + actor.getName(), HttpStatus.OK);
    }
}
