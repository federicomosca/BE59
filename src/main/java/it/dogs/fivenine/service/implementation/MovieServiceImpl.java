package it.dogs.fivenine.service.implementation;

import it.dogs.fivenine.model.domain.Movie;
import it.dogs.fivenine.repository.MovieRepository;
import it.dogs.fivenine.service.MovieService;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.time.LocalDate;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public Movie createMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    @Override
    public Movie updateMovie(Long id, Movie movie) {
        Optional<Movie> existingMovie = movieRepository.findById(id);
        if (existingMovie.isPresent()) {
            Movie movieToUpdate = existingMovie.get();
            movieToUpdate.setTitle(movie.getTitle());
            movieToUpdate.setGenres(movie.getGenres());
            movieToUpdate.setReleaseDate(movie.getReleaseDate());
            movieToUpdate.setPlotSummary(movie.getPlotSummary());
            movieToUpdate.setRuntimeMinutes(movie.getRuntimeMinutes());
            movieToUpdate.setCountries(movie.getCountries());
            movieToUpdate.setLanguages(movie.getLanguages());
            movieToUpdate.setAgeRating(movie.getAgeRating());
            return movieRepository.save(movieToUpdate);
        }
        return null;
    }

    @Override
    public void deleteMovie(Long id) {
        movieRepository.deleteById(id);
    }

    @Override
    public Movie getMovieById(Long id) {
        return movieRepository.findById(id).orElse(null);
    }
    
    @Override
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    @Override
    public List<Movie> searchMoviesByTitle(String title) {
        return movieRepository.findByTitleContainingIgnoreCase(title);
    }

    @Override
    public List<Movie> getMoviesByGenre(String genre) {
        return movieRepository.findByGenresContainingIgnoreCase(genre);
    }

    @Override
    public List<Movie> getMoviesByDirector(String director) {
        return movieRepository.findByDirectorNameContainingIgnoreCase(director);
    }

    @Override
    public List<Movie> getMoviesByReleaseDate(LocalDate releaseDate) {
        return movieRepository.findByReleaseDate(releaseDate);
    }

    @Override
    public List<Movie> getMoviesByDateRange(LocalDate startDate, LocalDate endDate) {
        return movieRepository.findByReleaseDateBetween(startDate, endDate);
    }

    @Override
    public List<Movie> searchMoviesWithFilters(String title, String genre, String director, LocalDate fromDate, LocalDate toDate) {
        return movieRepository.findMoviesWithFilters(title, genre, director, fromDate, toDate);
    }

    @Override
    public List<Movie> getRecentMovies() {
        return movieRepository.findRecentMovies();
    }

    @Override
    public List<Movie> getMoviesReleasedAfter(LocalDate date) {
        return movieRepository.findMoviesReleasedAfter(date);
    }
}
