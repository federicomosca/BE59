package it.dogs.fivenine.controller;

import it.dogs.fivenine.model.domain.User;
import it.dogs.fivenine.model.domain.UserConnection;
import it.dogs.fivenine.model.domain.enums.ConnectionStatus;
import it.dogs.fivenine.model.dto.ConnectionDTOs.ConnectionRequestDTO;
import it.dogs.fivenine.service.UserConnectionService;
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
@RequestMapping("/connections")
public class UserConnectionController {

    private final UserConnectionService connectionService;
    private final AuthenticationUtils authUtils;

    public UserConnectionController(UserConnectionService connectionService, AuthenticationUtils authUtils) {
        this.connectionService = connectionService;
        this.authUtils = authUtils;
    }

    @PostMapping("/request")
    public ResponseEntity<Map<String, String>> sendConnectionRequest(
            @Valid @RequestBody ConnectionRequestDTO dto, HttpServletRequest request) {
        Long userId = authUtils.getUserIdFromRequest(request);
        if (userId != null) {
            connectionService.sendConnectionRequest(userId, dto);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Connection request sent successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping("/{connectionId}/accept")
    public ResponseEntity<Map<String, String>> acceptConnection(
            @PathVariable Long connectionId, HttpServletRequest request) {
        Long userId = authUtils.getUserIdFromRequest(request);
        if (userId != null) {
            connectionService.acceptConnectionRequest(connectionId, userId);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Connection accepted successfully");
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping("/{connectionId}/reject")
    public ResponseEntity<Map<String, String>> rejectConnection(
            @PathVariable Long connectionId, HttpServletRequest request) {
        Long userId = authUtils.getUserIdFromRequest(request);
        if (userId != null) {
            connectionService.rejectConnectionRequest(connectionId, userId);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Connection rejected successfully");
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @DeleteMapping("/{connectionId}")
    public ResponseEntity<Void> removeConnection(
            @PathVariable Long connectionId, HttpServletRequest request) {
        Long userId = authUtils.getUserIdFromRequest(request);
        if (userId != null) {
            connectionService.removeConnection(connectionId, userId);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping("/block/{targetUserId}")
    public ResponseEntity<Map<String, String>> blockUser(
            @PathVariable Long targetUserId, HttpServletRequest request) {
        Long userId = authUtils.getUserIdFromRequest(request);
        if (userId != null) {
            connectionService.blockUser(userId, targetUserId);
            Map<String, String> response = new HashMap<>();
            response.put("message", "User blocked successfully");
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/my")
    public ResponseEntity<List<UserConnection>> getMyConnections(HttpServletRequest request) {
        Long userId = authUtils.getUserIdFromRequest(request);
        if (userId != null) {
            List<UserConnection> connections = connectionService.getUserConnections(userId);
            return ResponseEntity.ok(connections);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/friends")
    public ResponseEntity<List<User>> getFriends(HttpServletRequest request) {
        Long userId = authUtils.getUserIdFromRequest(request);
        if (userId != null) {
            List<User> friends = connectionService.getConnectedUsers(userId);
            return ResponseEntity.ok(friends);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/pending")
    public ResponseEntity<List<UserConnection>> getPendingRequests(HttpServletRequest request) {
        Long userId = authUtils.getUserIdFromRequest(request);
        if (userId != null) {
            List<UserConnection> pending = connectionService.getPendingRequests(userId);
            return ResponseEntity.ok(pending);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/pending/count")
    public ResponseEntity<Map<String, Long>> getPendingCount(HttpServletRequest request) {
        Long userId = authUtils.getUserIdFromRequest(request);
        if (userId != null) {
            long count = connectionService.countPendingRequests(userId);
            Map<String, Long> response = new HashMap<>();
            response.put("count", count);
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/status/{targetUserId}")
    public ResponseEntity<Map<String, String>> getConnectionStatus(
            @PathVariable Long targetUserId, HttpServletRequest request) {
        Long userId = authUtils.getUserIdFromRequest(request);
        if (userId != null) {
            ConnectionStatus status = connectionService.getConnectionStatus(userId, targetUserId);
            Map<String, String> response = new HashMap<>();
            response.put("status", status != null ? status.toString() : "NONE");
            response.put("connected", String.valueOf(connectionService.areUsersConnected(userId, targetUserId)));
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}