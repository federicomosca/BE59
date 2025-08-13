package it.dogs.fivenine.model.dto.RecommendationDTOs;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class MovieRecommendationDTO {
    
    @NotNull(message = "Recipient ID is required")
    private Long recipientId;
    
    @NotNull(message = "Movie ID is required")
    private Long movieId;
    
    @Size(max = 500, message = "Message must not exceed 500 characters")
    private String message;
    
    // Getters and Setters
    public Long getRecipientId() {
        return recipientId;
    }
    
    public void setRecipientId(Long recipientId) {
        this.recipientId = recipientId;
    }
    
    public Long getMovieId() {
        return movieId;
    }
    
    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
}