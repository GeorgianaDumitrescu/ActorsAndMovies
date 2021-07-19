CREATE TABLE movies
(
    id INT PRIMARY KEY UNIQUE,
    title varchar(50) NOT NULL,
    rating double precision(4,2) NOT NULL,
    type varchar(7) NOT NULL
);

CREATE TABLE actors
(
    id INT PRIMARY KEY UNIQUE,
    name varchar(50) NOT NULL,
    gender varchar(7) NOT NULL
);

CREATE TABLE MoviesAndActors
(
    movie_id INT NOT NULL REFERENCES movies (id),
    actor_id INT NOT NULL REFERENCES actors (id)
);