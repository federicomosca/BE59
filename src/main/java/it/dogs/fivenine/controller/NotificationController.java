package it.dogs.fivenine.controller;

import it.dogs.fivenine.model.domain.Notification;
import it.dogs.fivenine.service.NotificationService;
import it.dogs.fivenine.util.AuthenticationUtils;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;
    private final AuthenticationUtils authUtils;

    public NotificationController(NotificationService notificationService, AuthenticationUtils authUtils) {
        this.notificationService = notificationService;
        this.authUtils = authUtils;
    }

    @GetMapping
    public ResponseEntity<List<Notification>> getNotifications(HttpServletRequest request) {
        Long userId = authUtils.getUserIdFromRequest(request);
        if (userId != null) {
            List<Notification> notifications = notificationService.getUserNotifications(userId);
            return ResponseEntity.ok(notifications);
        }
        return ResponseEntity.status(401).build();
    }

    @GetMapping("/unread")
    public ResponseEntity<List<Notification>> getUnreadNotifications(HttpServletRequest request) {
        Long userId = authUtils.getUserIdFromRequest(request);
        if (userId != null) {
            List<Notification> notifications = notificationService.getUnreadNotifications(userId);
            return ResponseEntity.ok(notifications);
        }
        return ResponseEntity.status(401).build();
    }

    @GetMapping("/unread/count")
    public ResponseEntity<Map<String, Long>> getUnreadCount(HttpServletRequest request) {
        Long userId = authUtils.getUserIdFromRequest(request);
        if (userId != null) {
            long count = notificationService.countUnreadNotifications(userId);
            Map<String, Long> response = new HashMap<>();
            response.put("count", count);
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(401).build();
    }

    @PostMapping("/{notificationId}/read")
    public ResponseEntity<Map<String, String>> markAsRead(
            @PathVariable Long notificationId, HttpServletRequest request) {
        Long userId = authUtils.getUserIdFromRequest(request);
        if (userId != null) {
            notificationService.markAsRead(notificationId, userId);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Notification marked as read");
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(401).build();
    }

    @PostMapping("/read-all")
    public ResponseEntity<Map<String, String>> markAllAsRead(HttpServletRequest request) {
        Long userId = authUtils.getUserIdFromRequest(request);
        if (userId != null) {
            notificationService.markAllAsRead(userId);
            Map<String, String> response = new HashMap<>();
            response.put("message", "All notifications marked as read");
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(401).build();
    }

    @PostMapping("/{notificationId}/dismiss")
    public ResponseEntity<Map<String, String>> dismissNotification(
            @PathVariable Long notificationId, HttpServletRequest request) {
        Long userId = authUtils.getUserIdFromRequest(request);
        if (userId != null) {
            notificationService.dismissNotification(notificationId, userId);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Notification dismissed");
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(401).build();
    }
}