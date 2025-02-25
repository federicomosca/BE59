package it.dogs.fivenine.service;

import it.dogs.fivenine.model.Movie;
import org.springframework.stereotype.Service;


public interface MovieService {

    Movie createMovie(Movie movie);
    Movie getMovieById(Long id);
}
