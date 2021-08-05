package com.project.secondApp.models.Actor;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ActorRequest {
        private String name;
        private String gender;
        private List<String> movies;

        public ActorRequest() {
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

        public List<String> getMovies() {
            return movies;
        }

        public void setMovies(List<String> movies) {
            this.movies = movies;
        }
}
