package it.dogs.fivenine.controller;

import it.dogs.fivenine.model.domain.Comment;
import it.dogs.fivenine.model.domain.enums.CommentType;
import it.dogs.fivenine.model.dto.CommentDTOs.CommentDTO;
import it.dogs.fivenine.model.dto.CommentDTOs.CommentResponseDTO;
import it.dogs.fivenine.service.CommentService;
import it.dogs.fivenine.util.AuthenticationUtils;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/comments")
public class CommentController {
    
    private final CommentService commentService;
    private final AuthenticationUtils authUtils;
    
    public CommentController(CommentService commentService, AuthenticationUtils authUtils) {
        this.commentService = commentService;
        this.authUtils = authUtils;
    }
    
    // Movie Comments
    @PostMapping("/movies/{movieId}")
    public ResponseEntity<CommentResponseDTO> addMovieComment(
            @PathVariable Long movieId,
            @Valid @RequestBody CommentDTO commentDTO,
            HttpServletRequest request) {
        
        Long userId = authUtils.getUserIdFromRequest(request);
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        
        Comment comment = commentService.createComment(userId, CommentType.MOVIE, movieId, commentDTO);
        CommentResponseDTO responseDTO = convertToResponseDTO(comment);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }
    
    @GetMapping("/movies/{movieId}")
    public ResponseEntity<Page<CommentResponseDTO>> getMovieComments(
            @PathVariable Long movieId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
                Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        
        Page<CommentResponseDTO> comments = commentService.getEntityComments(CommentType.MOVIE, movieId, pageable);
        return ResponseEntity.ok(comments);
    }
    
    @GetMapping("/movies/{movieId}/count")
    public ResponseEntity<Map<String, Long>> getMovieCommentCount(@PathVariable Long movieId) {
        Long count = commentService.getCommentCount(CommentType.MOVIE, movieId);
        return ResponseEntity.ok(Map.of("commentCount", count));
    }
    
    // Person Comments
    @PostMapping("/persons/{personId}")
    public ResponseEntity<CommentResponseDTO> addPersonComment(
            @PathVariable Long personId,
            @Valid @RequestBody CommentDTO commentDTO,
            HttpServletRequest request) {
        
        Long userId = authUtils.getUserIdFromRequest(request);
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        
        Comment comment = commentService.createComment(userId, CommentType.PERSON, personId, commentDTO);
        CommentResponseDTO responseDTO = convertToResponseDTO(comment);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }
    
    @GetMapping("/persons/{personId}")
    public ResponseEntity<Page<CommentResponseDTO>> getPersonComments(
            @PathVariable Long personId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
                Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        
        Page<CommentResponseDTO> comments = commentService.getEntityComments(CommentType.PERSON, personId, pageable);
        return ResponseEntity.ok(comments);
    }
    
    // Collection Comments
    @PostMapping("/collections/{collectionId}")
    public ResponseEntity<CommentResponseDTO> addCollectionComment(
            @PathVariable Long collectionId,
            @Valid @RequestBody CommentDTO commentDTO,
            HttpServletRequest request) {
        
        Long userId = authUtils.getUserIdFromRequest(request);
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        
        Comment comment = commentService.createComment(userId, CommentType.COLLECTION, collectionId, commentDTO);
        CommentResponseDTO responseDTO = convertToResponseDTO(comment);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }
    
    @GetMapping("/collections/{collectionId}")
    public ResponseEntity<Page<CommentResponseDTO>> getCollectionComments(
            @PathVariable Long collectionId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
                Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        
        Page<CommentResponseDTO> comments = commentService.getEntityComments(CommentType.COLLECTION, collectionId, pageable);
        return ResponseEntity.ok(comments);
    }
    
    // Generic Comment Operations
    @PutMapping("/{commentId}")
    public ResponseEntity<CommentResponseDTO> updateComment(
            @PathVariable Long commentId,
            @Valid @RequestBody CommentDTO commentDTO,
            HttpServletRequest request) {
        
        Long userId = authUtils.getUserIdFromRequest(request);
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        
        Comment comment = commentService.updateComment(commentId, userId, commentDTO);
        CommentResponseDTO responseDTO = convertToResponseDTO(comment);
        
        return ResponseEntity.ok(responseDTO);
    }
    
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long commentId,
            HttpServletRequest request) {
        
        Long userId = authUtils.getUserIdFromRequest(request);
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        
        commentService.deleteComment(commentId, userId);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/my-comments")
    public ResponseEntity<Page<CommentResponseDTO>> getMyComments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir,
            HttpServletRequest request) {
        
        Long userId = authUtils.getUserIdFromRequest(request);
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
                Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        
        Page<CommentResponseDTO> comments = commentService.getUserComments(userId, pageable);
        return ResponseEntity.ok(comments);
    }
    
    @GetMapping("/{commentId}/replies")
    public ResponseEntity<List<CommentResponseDTO>> getCommentReplies(@PathVariable Long commentId) {
        List<CommentResponseDTO> replies = commentService.getCommentReplies(commentId);
        return ResponseEntity.ok(replies);
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
}