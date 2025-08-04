package it.dogs.fivenine.service.implementation;

import it.dogs.fivenine.exception.InvalidOperationException;
import it.dogs.fivenine.exception.ResourceNotFoundException;
import it.dogs.fivenine.model.domain.Comment;
import it.dogs.fivenine.model.domain.User;
import it.dogs.fivenine.model.domain.enums.CommentType;
import it.dogs.fivenine.model.dto.CommentDTOs.CommentDTO;
import it.dogs.fivenine.model.dto.CommentDTOs.CommentResponseDTO;
import it.dogs.fivenine.repository.CommentRepository;
import it.dogs.fivenine.repository.UserRepository;
import it.dogs.fivenine.repository.MovieRepository;
import it.dogs.fivenine.repository.CollectionRepository;
import it.dogs.fivenine.service.CommentService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {
    
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;
    private final CollectionRepository collectionRepository;
    
    public CommentServiceImpl(CommentRepository commentRepository,
                            UserRepository userRepository,
                            MovieRepository movieRepository,
                            CollectionRepository collectionRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.movieRepository = movieRepository;
        this.collectionRepository = collectionRepository;
    }
    
    @Override
    public Comment createComment(Long userId, CommentType commentType, Long entityId, CommentDTO commentDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        
        if (!isValidEntity(commentType, entityId)) {
            throw new ResourceNotFoundException("Entity not found: " + commentType + " with id: " + entityId);
        }
        
        Comment parentComment = null;
        if (commentDTO.getParentCommentId() != null) {
            parentComment = commentRepository.findById(commentDTO.getParentCommentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Parent comment not found with id: " + commentDTO.getParentCommentId()));
            
            // Verify parent comment is for the same entity
            if (!parentComment.getCommentType().equals(commentType) || !parentComment.getEntityId().equals(entityId)) {
                throw new InvalidOperationException("Parent comment must be for the same entity");
            }
        }
        
        Comment comment = new Comment(user, commentType, entityId, commentDTO.getContent(), parentComment);
        return commentRepository.save(comment);
    }
    
    @Override
    public Comment updateComment(Long commentId, Long userId, CommentDTO commentDTO) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with id: " + commentId));
        
        if (!comment.getUser().getId().equals(userId)) {
            throw new InvalidOperationException("User can only update their own comments");
        }
        
        comment.setContent(commentDTO.getContent());
        return commentRepository.save(comment);
    }
    
    @Override
    public void deleteComment(Long commentId, Long userId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with id: " + commentId));
        
        if (!comment.getUser().getId().equals(userId)) {
            throw new InvalidOperationException("User can only delete their own comments");
        }
        
        commentRepository.delete(comment);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<Comment> getCommentById(Long commentId) {
        return commentRepository.findById(commentId);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<CommentResponseDTO> getEntityComments(CommentType commentType, Long entityId) {
        List<Comment> parentComments = commentRepository.findByCommentTypeAndEntityIdAndParentCommentIsNull(commentType, entityId);
        return parentComments.stream()
                .map(this::convertToResponseDTOWithReplies)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<CommentResponseDTO> getEntityComments(CommentType commentType, Long entityId, Pageable pageable) {
        Page<Comment> comments = commentRepository.findByCommentTypeAndEntityIdAndParentCommentIsNull(commentType, entityId, pageable);
        return comments.map(this::convertToResponseDTOWithReplies);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<CommentResponseDTO> getUserComments(Long userId) {
        List<Comment> comments = commentRepository.findByUserIdOrderByCreatedAtDesc(userId);
        return comments.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<CommentResponseDTO> getUserComments(Long userId, Pageable pageable) {
        Page<Comment> comments = commentRepository.findByUserIdOrderByCreatedAtDesc(userId, pageable);
        return comments.map(this::convertToResponseDTO);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<CommentResponseDTO> getCommentReplies(Long parentCommentId) {
        Comment parentComment = commentRepository.findById(parentCommentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with id: " + parentCommentId));
        
        List<Comment> replies = commentRepository.findByParentCommentOrderByCreatedAtAsc(parentComment);
        return replies.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public Long getCommentCount(CommentType commentType, Long entityId) {
        return commentRepository.countByCommentTypeAndEntityId(commentType, entityId);
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean canUserModifyComment(Long commentId, Long userId) {
        Optional<Comment> comment = commentRepository.findById(commentId);
        return comment.isPresent() && comment.get().getUser().getId().equals(userId);
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean isValidEntity(CommentType commentType, Long entityId) {
        switch (commentType) {
            case MOVIE:
                return movieRepository.existsById(entityId);
            case COLLECTION:
                return collectionRepository.existsById(entityId);
            case PERSON:
                // TODO: Add PersonRepository when implemented
                return true; // For now, assume valid
            default:
                return false;
        }
    }
    
    private CommentResponseDTO convertToResponseDTO(Comment comment) {
        return new CommentResponseDTO(
                comment.getId(),
                comment.getUser().getUsername(),
                comment.getCommentType(),
                comment.getEntityId(),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getUpdatedAt(),
                comment.getParentComment() != null ? comment.getParentComment().getId() : null
        );
    }
    
    private CommentResponseDTO convertToResponseDTOWithReplies(Comment comment) {
        CommentResponseDTO dto = convertToResponseDTO(comment);
        
        // Load replies for parent comments
        if (comment.getParentComment() == null) {
            List<Comment> replies = commentRepository.findByParentCommentOrderByCreatedAtAsc(comment);
            List<CommentResponseDTO> replyDTOs = replies.stream()
                    .map(this::convertToResponseDTO)
                    .collect(Collectors.toList());
            dto.setReplies(replyDTOs);
        }
        
        return dto;
    }
}