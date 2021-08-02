package com.project.secondApp.controllers;

import com.project.secondApp.models.*;
import com.project.secondApp.services.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("api/v2/actors")
public class ActorsController {

    @Autowired
    ActorService actorService;

    // LIST ACTORS
    @GetMapping
    public ResponseEntity<List<ActorDto>> list() {

        List<ActorDto> actors = actorService.getActors();
        return ResponseEntity.ok(actors);
    }

    // GET ACTOR
    @GetMapping
    @RequestMapping("{name}")
    public ResponseEntity<ActorDto> getActor(@PathVariable String name) {

        ActorDto responseActor = actorService.getActor(name);
        return new ResponseEntity<>(responseActor, HttpStatus.OK);
    }

    // ADD ACTOR
    @PostMapping
    public ResponseEntity<ActorDto> create(@RequestBody final ActorDto newActor) throws FailedDatabaseException {

        actorService.addActor(newActor);
        return ResponseEntity.ok(newActor);
    }

    // DELETE ACTOR
    @RequestMapping(value = "{name}", method = RequestMethod.DELETE)
    public ResponseEntity<ActorDto> delete(@PathVariable String name) throws FailedDatabaseException  {

        ActorDto deletedActor = actorService.deleteActor(name);
        return ResponseEntity.ok(deletedActor);
    }

    // UPDATE ACTOR
    @RequestMapping(value = "{name}", method = RequestMethod.PATCH)
    public ResponseEntity.BodyBuilder update(@PathVariable String name, @RequestBody ActorDto updatedActor)  throws FailedDatabaseException {

        ActorDto actor = actorService.updateActor(name, updatedActor);
        return ResponseEntity.ok();
    }
}
