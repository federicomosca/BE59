package it.dogs.fivenine.service;

import it.dogs.fivenine.model.domain.MovieRecommendation;
import it.dogs.fivenine.model.dto.RecommendationDTOs.MovieRecommendationDTO;

import java.util.List;

public interface MovieRecommendationService {
    
    void recommendMovie(Long recommenderId, MovieRecommendationDTO dto);
    
    List<MovieRecommendation> getReceivedRecommendations(Long userId);
    
    List<MovieRecommendation> getSentRecommendations(Long userId);
    
    long countUnviewedRecommendations(Long userId);
    
    void markAsViewed(Long recommendationId, Long userId);
    
    void dismissRecommendation(Long recommendationId, Long userId);
}