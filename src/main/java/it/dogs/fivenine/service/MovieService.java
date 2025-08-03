package it.dogs.fivenine.service;

import it.dogs.fivenine.model.domain.Movie;
import java.util.List;

public interface MovieService {

    Movie createMovie(Movie movie);

    Movie getMovieById(Long id);
    
    List<Movie> getAllMovies();
}
