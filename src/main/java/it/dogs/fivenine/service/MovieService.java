package it.dogs.fivenine.service;

import it.dogs.fivenine.model.domain.Movie;

public interface MovieService {

    Movie createMovie(Movie movie);

    Movie getMovieById(Long id);
}
