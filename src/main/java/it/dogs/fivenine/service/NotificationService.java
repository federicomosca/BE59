package it.dogs.fivenine.service;

import it.dogs.fivenine.model.domain.User;
import it.dogs.fivenine.model.domain.Notification;
import it.dogs.fivenine.model.domain.Movie;
import it.dogs.fivenine.model.domain.Collection;
import it.dogs.fivenine.model.domain.enums.NotificationType;

import java.util.List;

public interface NotificationService {
    
    void createNotification(User recipient, User actor, NotificationType type, 
                          String title, String message, String entityType, Long entityId);
    
    void createConnectionRequestNotification(User recipient, User requester);
    
    void createConnectionAcceptedNotification(User recipient, User accepter);
    
    void createNewMessageNotification(User recipient, User sender);
    
    void createCollectionSharedNotification(User recipient, User sharer, Collection collection);
    
    void createMovieRecommendationNotification(User recipient, User recommender, Movie movie);
    
    void createCommentNotification(User recipient, User commenter, String entityType, Long entityId);
    
    void createMovieRequestNotification(User recipient, String action, Movie movie);
    
    List<Notification> getUserNotifications(Long userId);
    
    List<Notification> getUnreadNotifications(Long userId);
    
    long countUnreadNotifications(Long userId);
    
    void markAsRead(Long notificationId, Long userId);
    
    void markAllAsRead(Long userId);
    
    void dismissNotification(Long notificationId, Long userId);
}