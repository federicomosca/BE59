package it.dogs.fivenine.model.dto.ConnectionDTOs;

import jakarta.validation.constraints.NotNull;

public class ConnectionRequestDTO {
    
    @NotNull(message = "Target user ID is required")
    private Long targetUserId;
    
    private String message;
    
    // Getters and Setters
    public Long getTargetUserId() {
        return targetUserId;
    }
    
    public void setTargetUserId(Long targetUserId) {
        this.targetUserId = targetUserId;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
}