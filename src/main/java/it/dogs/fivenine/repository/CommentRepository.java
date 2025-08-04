package it.dogs.fivenine.repository;

import it.dogs.fivenine.model.domain.Comment;
import it.dogs.fivenine.model.domain.enums.CommentType;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    
    List<Comment> findByCommentTypeAndEntityIdAndParentCommentIsNull(CommentType commentType, Long entityId);
    
    Page<Comment> findByCommentTypeAndEntityIdAndParentCommentIsNull(CommentType commentType, Long entityId, Pageable pageable);
    
    List<Comment> findByParentCommentOrderByCreatedAtAsc(Comment parentComment);
    
    List<Comment> findByUserIdOrderByCreatedAtDesc(Long userId);
    
    Page<Comment> findByUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable);
    
    @Query("SELECT c FROM Comment c WHERE c.commentType = :commentType AND c.entityId = :entityId ORDER BY c.createdAt DESC")
    List<Comment> findByCommentTypeAndEntityIdOrderByCreatedAtDesc(@Param("commentType") CommentType commentType, @Param("entityId") Long entityId);
    
    @Query("SELECT c FROM Comment c WHERE c.commentType = :commentType AND c.entityId = :entityId ORDER BY c.createdAt ASC")
    List<Comment> findByCommentTypeAndEntityIdOrderByCreatedAtAsc(@Param("commentType") CommentType commentType, @Param("entityId") Long entityId);
    
    @Query("SELECT COUNT(c) FROM Comment c WHERE c.commentType = :commentType AND c.entityId = :entityId")
    Long countByCommentTypeAndEntityId(@Param("commentType") CommentType commentType, @Param("entityId") Long entityId);
    
    @Query("SELECT c FROM Comment c WHERE c.commentType = :commentType AND c.entityId IN :entityIds")
    List<Comment> findByCommentTypeAndEntityIds(@Param("commentType") CommentType commentType, @Param("entityIds") List<Long> entityIds);
}