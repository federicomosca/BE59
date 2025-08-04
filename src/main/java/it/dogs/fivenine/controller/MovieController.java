package it.dogs.fivenine.controller;

import it.dogs.fivenine.model.domain.Movie;
import it.dogs.fivenine.service.MovieService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.time.LocalDate;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public ResponseEntity<List<Movie>> getAllMovies() {
        List<Movie> movies = movieService.getAllMovies();
        return ResponseEntity.ok(movies);
    }

    @PostMapping
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie) {
        Movie createdMovie = movieService.createMovie(movie);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMovie);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable Long id) {
        Movie movie = movieService.getMovieById(id);
        if (movie != null) {
            return ResponseEntity.ok(movie);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable Long id, @RequestBody Movie movie) {
        Movie updatedMovie = movieService.updateMovie(id, movie);
        if (updatedMovie != null) {
            return ResponseEntity.ok(updatedMovie);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<Movie>> searchMovies(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) String director,
            @RequestParam(required = false) String fromDate,
            @RequestParam(required = false) String toDate) {
        
        LocalDate from = fromDate != null ? LocalDate.parse(fromDate) : null;
        LocalDate to = toDate != null ? LocalDate.parse(toDate) : null;
        
        List<Movie> movies = movieService.searchMoviesWithFilters(title, genre, director, from, to);
        return ResponseEntity.ok(movies);
    }

    @GetMapping("/search/title")
    public ResponseEntity<List<Movie>> searchByTitle(@RequestParam String title) {
        List<Movie> movies = movieService.searchMoviesByTitle(title);
        return ResponseEntity.ok(movies);
    }

    @GetMapping("/search/genre")
    public ResponseEntity<List<Movie>> searchByGenre(@RequestParam String genre) {
        List<Movie> movies = movieService.getMoviesByGenre(genre);
        return ResponseEntity.ok(movies);
    }

    @GetMapping("/search/director")
    public ResponseEntity<List<Movie>> searchByDirector(@RequestParam String director) {
        List<Movie> movies = movieService.getMoviesByDirector(director);
        return ResponseEntity.ok(movies);
    }

    @GetMapping("/recent")
    public ResponseEntity<List<Movie>> getRecentMovies() {
        List<Movie> movies = movieService.getRecentMovies();
        return ResponseEntity.ok(movies);
    }

    @GetMapping("/released-after")
    public ResponseEntity<List<Movie>> getMoviesReleasedAfter(@RequestParam String date) {
        LocalDate releaseDate = LocalDate.parse(date);
        List<Movie> movies = movieService.getMoviesReleasedAfter(releaseDate);
        return ResponseEntity.ok(movies);
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<Movie>> getMoviesByDateRange(
            @RequestParam String startDate,
            @RequestParam String endDate) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        List<Movie> movies = movieService.getMoviesByDateRange(start, end);
        return ResponseEntity.ok(movies);
    }
}
