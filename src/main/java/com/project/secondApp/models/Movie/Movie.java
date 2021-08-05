package com.project.secondApp.models.Movie;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project.secondApp.models.Actor.Actor;

import javax.persistence.*;
import java.util.List;

enum Type {
    horror,
    comedy,
    action
}

@Entity(name = "movies")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private Double rating;

    @Enumerated(EnumType.STRING)
    private Type type;

    @ManyToMany
    @JoinTable(
            name = "MoviesAndActors",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id"))
    private List<Actor> actors;

    public Movie() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Type getType() {
        return type;
    }
    public String getTypeString() {
        return type.toString();
    }

    public void setType(Type type) {
        this.type = type;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }
}
