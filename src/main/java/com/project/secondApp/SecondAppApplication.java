package com.project.secondApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication (scanBasePackages={
		"com.project.secondApp.repositories.ActorRepository",
		"com.project.secondApp.repositories.MovieRepository"})
public class SecondAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecondAppApplication.class, args);
	}

}
