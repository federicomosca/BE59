package it.dogs.fivenine.repository;

import it.dogs.fivenine.model.domain.PrivateMessage;
import it.dogs.fivenine.model.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrivateMessageRepository extends JpaRepository<PrivateMessage, Long> {
    
    @Query("SELECT pm FROM PrivateMessage pm WHERE pm.recipient = :user AND pm.isDeletedByRecipient = false ORDER BY pm.sentAt DESC")
    List<PrivateMessage> findInboxMessages(@Param("user") User user);
    
    @Query("SELECT pm FROM PrivateMessage pm WHERE pm.sender = :user AND pm.isDeletedBySender = false ORDER BY pm.sentAt DESC")
    List<PrivateMessage> findSentMessages(@Param("user") User user);
    
    @Query("SELECT COUNT(pm) FROM PrivateMessage pm WHERE pm.recipient = :user AND pm.readAt IS NULL AND pm.isDeletedByRecipient = false")
    long countUnreadMessages(@Param("user") User user);
    
    @Query("SELECT pm FROM PrivateMessage pm WHERE pm.recipient = :user AND pm.readAt IS NULL AND pm.isDeletedByRecipient = false ORDER BY pm.sentAt DESC")
    List<PrivateMessage> findUnreadMessages(@Param("user") User user);
    
    @Query("SELECT pm FROM PrivateMessage pm WHERE ((pm.sender = :user1 AND pm.recipient = :user2) OR (pm.sender = :user2 AND pm.recipient = :user1)) " +
           "AND ((pm.sender = :user1 AND pm.isDeletedBySender = false) OR (pm.sender = :user2 AND pm.isDeletedBySender = false)) " +
           "AND ((pm.recipient = :user1 AND pm.isDeletedByRecipient = false) OR (pm.recipient = :user2 AND pm.isDeletedByRecipient = false)) " +
           "ORDER BY pm.sentAt ASC")
    List<PrivateMessage> findConversationBetween(@Param("user1") User user1, @Param("user2") User user2);
}