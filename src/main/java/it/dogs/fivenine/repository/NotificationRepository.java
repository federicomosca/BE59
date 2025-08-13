package it.dogs.fivenine.repository;

import it.dogs.fivenine.model.domain.Notification;
import it.dogs.fivenine.model.domain.User;
import it.dogs.fivenine.model.domain.enums.NotificationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    
    @Query("SELECT n FROM Notification n WHERE n.recipient = :user AND n.isDismissed = false ORDER BY n.createdAt DESC")
    List<Notification> findActiveNotifications(@Param("user") User user);
    
    @Query("SELECT COUNT(n) FROM Notification n WHERE n.recipient = :user AND n.readAt IS NULL AND n.isDismissed = false")
    long countUnreadNotifications(@Param("user") User user);
    
    @Query("SELECT n FROM Notification n WHERE n.recipient = :user AND n.readAt IS NULL AND n.isDismissed = false ORDER BY n.createdAt DESC")
    List<Notification> findUnreadNotifications(@Param("user") User user);
    
    List<Notification> findByRecipientAndTypeOrderByCreatedAtDesc(User recipient, NotificationType type);
    
    @Query("SELECT n FROM Notification n WHERE n.recipient = :user ORDER BY n.createdAt DESC")
    List<Notification> findAllNotificationsForUser(@Param("user") User user);
}