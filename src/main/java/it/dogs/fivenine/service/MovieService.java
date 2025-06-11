package it.dogs.fivenine.service;

import it.dogs.fivenine.model.Movie;

public interface MovieService {

    Movie createMovie(Movie movie);

    Movie getMovieById(Long id);
}
