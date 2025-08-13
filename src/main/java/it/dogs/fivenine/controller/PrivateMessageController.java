package it.dogs.fivenine.controller;

import it.dogs.fivenine.model.domain.PrivateMessage;
import it.dogs.fivenine.model.dto.MessageDTOs.PrivateMessageDTO;
import it.dogs.fivenine.service.PrivateMessageService;
import it.dogs.fivenine.util.AuthenticationUtils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/messages")
public class PrivateMessageController {

    private final PrivateMessageService messageService;
    private final AuthenticationUtils authUtils;

    public PrivateMessageController(PrivateMessageService messageService, AuthenticationUtils authUtils) {
        this.messageService = messageService;
        this.authUtils = authUtils;
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> sendMessage(
            @Valid @RequestBody PrivateMessageDTO dto, HttpServletRequest request) {
        Long userId = authUtils.getUserIdFromRequest(request);
        if (userId != null) {
            messageService.sendMessage(userId, dto);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Message sent successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/inbox")
    public ResponseEntity<List<PrivateMessage>> getInbox(HttpServletRequest request) {
        Long userId = authUtils.getUserIdFromRequest(request);
        if (userId != null) {
            List<PrivateMessage> messages = messageService.getInboxMessages(userId);
            return ResponseEntity.ok(messages);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/sent")
    public ResponseEntity<List<PrivateMessage>> getSentMessages(HttpServletRequest request) {
        Long userId = authUtils.getUserIdFromRequest(request);
        if (userId != null) {
            List<PrivateMessage> messages = messageService.getSentMessages(userId);
            return ResponseEntity.ok(messages);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/conversation/{otherUserId}")
    public ResponseEntity<List<PrivateMessage>> getConversation(
            @PathVariable Long otherUserId, HttpServletRequest request) {
        Long userId = authUtils.getUserIdFromRequest(request);
        if (userId != null) {
            List<PrivateMessage> conversation = messageService.getConversation(userId, otherUserId);
            return ResponseEntity.ok(conversation);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/unread/count")
    public ResponseEntity<Map<String, Long>> getUnreadCount(HttpServletRequest request) {
        Long userId = authUtils.getUserIdFromRequest(request);
        if (userId != null) {
            long count = messageService.countUnreadMessages(userId);
            Map<String, Long> response = new HashMap<>();
            response.put("count", count);
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/{messageId}")
    public ResponseEntity<PrivateMessage> getMessage(
            @PathVariable Long messageId, HttpServletRequest request) {
        Long userId = authUtils.getUserIdFromRequest(request);
        if (userId != null) {
            PrivateMessage message = messageService.getMessage(messageId, userId);
            return ResponseEntity.ok(message);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping("/{messageId}/read")
    public ResponseEntity<Map<String, String>> markAsRead(
            @PathVariable Long messageId, HttpServletRequest request) {
        Long userId = authUtils.getUserIdFromRequest(request);
        if (userId != null) {
            messageService.markAsRead(messageId, userId);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Message marked as read");
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @DeleteMapping("/{messageId}")
    public ResponseEntity<Void> deleteMessage(
            @PathVariable Long messageId, 
            @RequestParam(defaultValue = "true") boolean fromInbox,
            HttpServletRequest request) {
        Long userId = authUtils.getUserIdFromRequest(request);
        if (userId != null) {
            messageService.deleteMessage(messageId, userId, fromInbox);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}