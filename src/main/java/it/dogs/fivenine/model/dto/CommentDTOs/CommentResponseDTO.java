package it.dogs.fivenine.model.dto.CommentDTOs;

import it.dogs.fivenine.model.domain.enums.CommentType;
import java.time.LocalDateTime;
import java.util.List;

public class CommentResponseDTO {
    
    private Long id;
    private String username;
    private CommentType commentType;
    private Long entityId;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long parentCommentId;
    private List<CommentResponseDTO> replies;
    
    // Constructors
    public CommentResponseDTO() {}
    
    public CommentResponseDTO(Long id, String username, CommentType commentType, Long entityId,
                            String content, LocalDateTime createdAt, LocalDateTime updatedAt,
                            Long parentCommentId) {
        this.id = id;
        this.username = username;
        this.commentType = commentType;
        this.entityId = entityId;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.parentCommentId = parentCommentId;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public CommentType getCommentType() {
        return commentType;
    }
    
    public void setCommentType(CommentType commentType) {
        this.commentType = commentType;
    }
    
    public Long getEntityId() {
        return entityId;
    }
    
    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    public Long getParentCommentId() {
        return parentCommentId;
    }
    
    public void setParentCommentId(Long parentCommentId) {
        this.parentCommentId = parentCommentId;
    }
    
    public List<CommentResponseDTO> getReplies() {
        return replies;
    }
    
    public void setReplies(List<CommentResponseDTO> replies) {
        this.replies = replies;
    }
}