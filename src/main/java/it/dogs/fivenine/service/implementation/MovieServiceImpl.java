package it.dogs.fivenine.service.implementation;

import it.dogs.fivenine.model.domain.Movie;
import it.dogs.fivenine.repository.MovieRepository;
import it.dogs.fivenine.service.MovieService;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Movie createMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    public Movie getMovieById(Long id) {
        return movieRepository.findById(id).orElse(null);
    }
    
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }
}
