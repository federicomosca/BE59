package it.dogs.fivenine.service;

import it.dogs.fivenine.model.domain.Comment;
import it.dogs.fivenine.model.domain.enums.CommentType;
import it.dogs.fivenine.model.dto.CommentDTOs.CommentDTO;
import it.dogs.fivenine.model.dto.CommentDTOs.CommentResponseDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    
    Comment createComment(Long userId, CommentType commentType, Long entityId, CommentDTO commentDTO);
    
    Comment updateComment(Long commentId, Long userId, CommentDTO commentDTO);
    
    void deleteComment(Long commentId, Long userId);
    
    Optional<Comment> getCommentById(Long commentId);
    
    List<CommentResponseDTO> getEntityComments(CommentType commentType, Long entityId);
    
    Page<CommentResponseDTO> getEntityComments(CommentType commentType, Long entityId, Pageable pageable);
    
    List<CommentResponseDTO> getUserComments(Long userId);
    
    Page<CommentResponseDTO> getUserComments(Long userId, Pageable pageable);
    
    List<CommentResponseDTO> getCommentReplies(Long parentCommentId);
    
    Long getCommentCount(CommentType commentType, Long entityId);
    
    boolean canUserModifyComment(Long commentId, Long userId);
    
    boolean isValidEntity(CommentType commentType, Long entityId);
}