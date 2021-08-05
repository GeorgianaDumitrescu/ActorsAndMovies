package com.project.secondApp.models.Actor;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project.secondApp.models.Movie.Movie;

import javax.persistence.*;
import java.util.List;

@Entity(name = "actors")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Actor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String gender;


    @ManyToMany
    @JoinTable(
            name = "MoviesAndActors",
            joinColumns = @JoinColumn(name = "actor_id"),
            inverseJoinColumns = @JoinColumn(name = "movie_id"))
    private List<Movie> movies;

    public Actor() {
    }

    public Long getId() {
        return id;
    }

    /* Possibly redundant (?)*/
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}
