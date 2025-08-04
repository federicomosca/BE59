package it.dogs.fivenine.service;

import it.dogs.fivenine.model.domain.Rating;
import it.dogs.fivenine.model.dto.RatingDTOs.RatingDTO;
import it.dogs.fivenine.model.dto.RatingDTOs.RatingResponseDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface RatingService {
    
    Rating createOrUpdateRating(Long userId, Long movieId, RatingDTO ratingDTO);
    
    Optional<Rating> getUserRatingForMovie(Long userId, Long movieId);
    
    List<RatingResponseDTO> getMovieRatings(Long movieId);
    
    Page<RatingResponseDTO> getMovieRatings(Long movieId, Pageable pageable);
    
    List<RatingResponseDTO> getUserRatings(Long userId);
    
    Page<RatingResponseDTO> getUserRatings(Long userId, Pageable pageable);
    
    Double getAverageRating(Long movieId);
    
    Long getRatingCount(Long movieId);
    
    void deleteRating(Long userId, Long movieId);
    
    boolean hasUserRatedMovie(Long userId, Long movieId);
}