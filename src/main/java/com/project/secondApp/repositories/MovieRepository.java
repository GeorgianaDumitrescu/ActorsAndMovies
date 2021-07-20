package com.project.secondApp.repositories;

import com.project.secondApp.models.Movie;
import org.hibernate.criterion.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    List<Movie> findAll();
    void deleteById(Long id);
    Movie getOne(Long id);
    Movie saveAndFlush(Movie existingSession);
}
