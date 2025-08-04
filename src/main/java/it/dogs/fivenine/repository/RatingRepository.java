package it.dogs.fivenine.repository;

import it.dogs.fivenine.model.domain.Rating;
import it.dogs.fivenine.model.domain.Movie;
import it.dogs.fivenine.model.domain.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
    
    Optional<Rating> findByUserAndMovie(User user, Movie movie);
    
    List<Rating> findByMovie(Movie movie);
    
    Page<Rating> findByMovie(Movie movie, Pageable pageable);
    
    List<Rating> findByUser(User user);
    
    Page<Rating> findByUser(User user, Pageable pageable);
    
    @Query("SELECT AVG(r.rating) FROM Rating r WHERE r.movie = :movie")
    Double findAverageRatingByMovie(@Param("movie") Movie movie);
    
    @Query("SELECT COUNT(r) FROM Rating r WHERE r.movie = :movie")
    Long countRatingsByMovie(@Param("movie") Movie movie);
    
    @Query("SELECT r FROM Rating r WHERE r.movie = :movie ORDER BY r.createdAt DESC")
    List<Rating> findByMovieOrderByCreatedAtDesc(@Param("movie") Movie movie);
    
    @Query("SELECT r FROM Rating r WHERE r.movie = :movie ORDER BY r.rating DESC")
    List<Rating> findByMovieOrderByRatingDesc(@Param("movie") Movie movie);
    
    @Query("SELECT r FROM Rating r WHERE r.user.id = :userId AND r.movie.id = :movieId")
    Optional<Rating> findByUserIdAndMovieId(@Param("userId") Long userId, @Param("movieId") Long movieId);
}