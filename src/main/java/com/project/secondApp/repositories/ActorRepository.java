package com.project.secondApp.repositories;

import com.project.secondApp.models.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ActorRepository extends JpaRepository<Actor, Long> {

    List<Actor> findAll();
    void deleteById(Long id);
    Actor getOne(Long id);
    Actor saveAndFlush(Actor existingActor);

    /* https://www.netsurfingzone.com/jpa/how-to-write-custom-method-in-repository-in-spring-data-jpa/ */
    Actor findByName(String name);
}
