package it.dogs.fivenine.service;

import it.dogs.fivenine.model.domain.Movie;
import java.util.List;
import java.time.LocalDate;

public interface MovieService {

    Movie createMovie(Movie movie);
    Movie updateMovie(Long id, Movie movie);
    void deleteMovie(Long id);
    Movie getMovieById(Long id);
    List<Movie> getAllMovies();
    List<Movie> searchMoviesByTitle(String title);
    List<Movie> getMoviesByGenre(String genre);
    List<Movie> getMoviesByDirector(String director);
    List<Movie> getMoviesByReleaseDate(LocalDate releaseDate);
    List<Movie> getMoviesByDateRange(LocalDate startDate, LocalDate endDate);
    List<Movie> searchMoviesWithFilters(String title, String genre, String director, LocalDate fromDate, LocalDate toDate);
    List<Movie> getRecentMovies();
    List<Movie> getMoviesReleasedAfter(LocalDate date);
}
