CREATE TABLE movies
(
    id SERIAL PRIMARY KEY,
    title varchar(50) NOT NULL,
    rating double precision(4,2) NOT NULL,
    type varchar(7) NOT NULL
);

CREATE TABLE actors
(
    id SERIAL PRIMARY KEY,
    name varchar(50) NOT NULL,
    gender varchar(7) NOT NULL
);

CREATE TABLE movies_and_actors
(
    movie_id integer NOT NULL REFERENCES movies (id) ON DELETE CASCADE,
    actor_id integer NOT NULL REFERENCES actors (id) ON DELETE CASCADE
);
