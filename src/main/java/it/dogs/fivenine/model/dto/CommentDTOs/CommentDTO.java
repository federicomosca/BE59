package it.dogs.fivenine.model.dto.CommentDTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CommentDTO {
    
    @NotBlank(message = "Comment content is required")
    @Size(max = 2000, message = "Comment must be at most 2000 characters")
    private String content;
    
    private Long parentCommentId;
    
    // Constructors
    public CommentDTO() {}
    
    public CommentDTO(String content) {
        this.content = content;
    }
    
    public CommentDTO(String content, Long parentCommentId) {
        this.content = content;
        this.parentCommentId = parentCommentId;
    }
    
    // Getters and Setters
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    public Long getParentCommentId() {
        return parentCommentId;
    }
    
    public void setParentCommentId(Long parentCommentId) {
        this.parentCommentId = parentCommentId;
    }
}