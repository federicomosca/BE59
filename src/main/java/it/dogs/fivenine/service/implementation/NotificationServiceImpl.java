package it.dogs.fivenine.service.implementation;

import it.dogs.fivenine.model.domain.User;
import it.dogs.fivenine.model.domain.Notification;
import it.dogs.fivenine.model.domain.Movie;
import it.dogs.fivenine.model.domain.Collection;
import it.dogs.fivenine.model.domain.enums.NotificationType;
import it.dogs.fivenine.repository.NotificationRepository;
import it.dogs.fivenine.repository.UserRepository;
import it.dogs.fivenine.service.NotificationService;
import it.dogs.fivenine.exception.ResourceNotFoundException;
import it.dogs.fivenine.exception.InvalidOperationException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    public NotificationServiceImpl(NotificationRepository notificationRepository,
                                  UserRepository userRepository) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void createNotification(User recipient, User actor, NotificationType type, 
                                 String title, String message, String entityType, Long entityId) {
        Notification notification = new Notification();
        notification.setRecipient(recipient);
        notification.setActor(actor);
        notification.setType(type);
        notification.setTitle(title);
        notification.setMessage(message);
        notification.setEntityType(entityType);
        notification.setEntityId(entityId);
        notification.setCreatedAt(LocalDateTime.now());

        notificationRepository.save(notification);
    }

    @Override
    public void createConnectionRequestNotification(User recipient, User requester) {
        createNotification(
            recipient, 
            requester, 
            NotificationType.CONNECTION_REQUEST,
            "New Connection Request",
            requester.getUsername() + " wants to connect with you",
            "User",
            requester.getId()
        );
    }

    @Override
    public void createConnectionAcceptedNotification(User recipient, User accepter) {
        createNotification(
            recipient, 
            accepter, 
            NotificationType.CONNECTION_ACCEPTED,
            "Connection Accepted",
            accepter.getUsername() + " accepted your connection request",
            "User",
            accepter.getId()
        );
    }

    @Override
    public void createNewMessageNotification(User recipient, User sender) {
        createNotification(
            recipient, 
            sender, 
            NotificationType.NEW_MESSAGE,
            "New Message",
            "You received a new message from " + sender.getUsername(),
            "User",
            sender.getId()
        );
    }

    @Override
    public void createCollectionSharedNotification(User recipient, User sharer, Collection collection) {
        createNotification(
            recipient, 
            sharer, 
            NotificationType.COLLECTION_SHARED,
            "Collection Shared",
            sharer.getUsername() + " shared the collection \"" + collection.getName() + "\" with you",
            "Collection",
            collection.getId()
        );
    }

    @Override
    public void createMovieRecommendationNotification(User recipient, User recommender, Movie movie) {
        createNotification(
            recipient, 
            recommender, 
            NotificationType.MOVIE_RECOMMENDATION,
            "Movie Recommendation",
            recommender.getUsername() + " recommended \"" + movie.getTitle() + "\" to you",
            "Movie",
            movie.getId()
        );
    }

    @Override
    public void createCommentNotification(User recipient, User commenter, String entityType, Long entityId) {
        createNotification(
            recipient, 
            commenter, 
            NotificationType.NEW_COMMENT,
            "New Comment",
            commenter.getUsername() + " commented on your " + entityType.toLowerCase(),
            entityType,
            entityId
        );
    }

    @Override
    public void createMovieRequestNotification(User recipient, String action, Movie movie) {
        NotificationType type = action.equals("approved") ? 
            NotificationType.MOVIE_REQUEST_APPROVED : NotificationType.MOVIE_REQUEST_REJECTED;
        
        String title = "Movie Request " + action.substring(0, 1).toUpperCase() + action.substring(1);
        String message = "Your request for \"" + movie.getTitle() + "\" has been " + action;
        
        createNotification(
            recipient, 
            null, // System notification
            type,
            title,
            message,
            "Movie",
            movie.getId()
        );
    }

    @Override
    public List<Notification> getUserNotifications(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        
        return notificationRepository.findAllNotificationsForUser(user);
    }

    @Override
    public List<Notification> getUnreadNotifications(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        
        return notificationRepository.findUnreadNotifications(user);
    }

    @Override
    public long countUnreadNotifications(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        
        return notificationRepository.countUnreadNotifications(user);
    }

    @Override
    @Transactional
    public void markAsRead(Long notificationId, Long userId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found"));

        if (!notification.getRecipient().getId().equals(userId)) {
            throw new InvalidOperationException("You can only mark your own notifications as read");
        }

        if (notification.getReadAt() == null) {
            notification.setReadAt(LocalDateTime.now());
            notificationRepository.save(notification);
        }
    }

    @Override
    @Transactional
    public void markAllAsRead(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        
        List<Notification> unreadNotifications = notificationRepository.findUnreadNotifications(user);
        
        LocalDateTime now = LocalDateTime.now();
        unreadNotifications.forEach(notification -> notification.setReadAt(now));
        
        notificationRepository.saveAll(unreadNotifications);
    }

    @Override
    @Transactional
    public void dismissNotification(Long notificationId, Long userId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found"));

        if (!notification.getRecipient().getId().equals(userId)) {
            throw new InvalidOperationException("You can only dismiss your own notifications");
        }

        notification.setDismissed(true);
        notificationRepository.save(notification);
    }
}