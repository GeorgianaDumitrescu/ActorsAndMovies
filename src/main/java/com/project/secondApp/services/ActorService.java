package com.project.secondApp.services;

import com.project.secondApp.models.*;
import com.project.secondApp.repositories.ActorRepository;
import com.project.secondApp.repositories.MovieRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class ActorService {

    private ActorRepository actorRepository;
    private MovieRepository movieRepository;

    @Autowired
    public ActorService(ActorRepository actorRepository, MovieRepository movieRepository) {
        this.actorRepository = actorRepository;
        this.movieRepository = movieRepository;
    }

    public void validateActor(Actor actor) {

        if (actor.getName().matches(".*\\d.*")) {
            /* Actor name has incorrect format (no numbers allowed)*/
            throw new IncorrectNameFormatException("");
        }

        if (!(actor.getGender().equals("female") || actor.getGender().equals("male"))){
            /* Actor gender has incorrect type/format (no spaces allowed) */
            throw new IncorrectGenderFormatException("");
        }

        List<Movie> movies = actor.getMovies();

        for(Movie movie : movies) {
            if ((movie == null) || (movieRepository.findByTitle(movie.getTitle()) == null)) {
                /* Associated movie does not exist */
                throw new MovieNotFoundException("");
            }
        }

        /* Actor is valid */
    }

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

        List<String> movies = sourceActor.getMovies();

        for(String movie : movies) {
            Movie newMovie = movieRepository.findByTitle(movie);
            if (newMovie == null) {
                /* Movie does not exist */
                throw new MovieNotFoundException("");
            }
            actor.getMovies().add(newMovie);
        }

        return actor;
    }

    // GET ACTORS
    public List<ActorDto> getActors() {
        /* Find actors */
        List<Actor> actorsList = actorRepository.findAll();

        /* Map actors*/
        List<ActorDto> actors = new ArrayList<>();
        for(Actor actor : actorsList) {
            actors.add(getMapping(actor));
        }

        return actors;
    }

    // GET ACTOR
    public ActorDto getActor(String name) {
        Actor actor = actorRepository.findByName(name);

        if (actor == null) {
            throw new ActorNotFoundException("");
        }

        ActorDto responseActor = getMapping(actor);
        return responseActor;
    }

    // ADD ACTOR
    public Actor addActor(final ActorDto newActor) {

        Actor actor = getRawData(newActor);

        Actor oldActor = actorRepository.findByName(actor.getName());
        if(oldActor != null){
            /* Actor exists */
            throw new ActorAlreadyExistsException("");
        }

        validateActor(actor);

        /* Add actor */
        actorRepository.saveAndFlush(actor);
        return actor;
    }

    // DELETE ACTOR
    public ActorDto deleteActor(String name) throws FailedDatabaseException  {
        Actor deletedActor;
        ActorDto deletedActorMapping;

        // Write PathVariable as it is, no quotes (in Postman)
        deletedActor = actorRepository.findByName(name);
        if (deletedActor == null) {
            throw new ActorNotFoundException("");
        }
        deletedActorMapping = getMapping(deletedActor);
        actorRepository.deleteById(deletedActor.getId());

        return deletedActorMapping;
    }

    // UPDATE ACTOR
    public ActorDto updateActor(String name, ActorDto updatedActor)  throws FailedDatabaseException {

        Actor actor = getRawData(updatedActor);
        Actor existingActor = actorRepository.findByName(name);
        if(existingActor == null){
            /* Actor does not exist */
            throw new ActorNotFoundException("");
        }

        validateActor(actor);

        /* Keep old id */
        BeanUtils.copyProperties(actor, existingActor, "id");

        /* Save updated actor with old id */
        actorRepository.saveAndFlush(existingActor);

        return updatedActor;
    }
}
