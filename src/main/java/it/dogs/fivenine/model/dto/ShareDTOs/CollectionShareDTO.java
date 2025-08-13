package it.dogs.fivenine.model.dto.ShareDTOs;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CollectionShareDTO {
    
    @NotNull(message = "Collection ID is required")
    private Long collectionId;
    
    @NotNull(message = "Target user ID is required")
    private Long targetUserId;
    
    @Size(max = 500, message = "Message must not exceed 500 characters")
    private String message;
    
    // Getters and Setters
    public Long getCollectionId() {
        return collectionId;
    }
    
    public void setCollectionId(Long collectionId) {
        this.collectionId = collectionId;
    }
    
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