package it.dogs.fivenine.service.implementation;

import it.dogs.fivenine.model.domain.User;
import it.dogs.fivenine.model.domain.UserConnection;
import it.dogs.fivenine.model.domain.enums.ConnectionStatus;
import it.dogs.fivenine.model.dto.ConnectionDTOs.ConnectionRequestDTO;
import it.dogs.fivenine.repository.UserConnectionRepository;
import it.dogs.fivenine.repository.UserRepository;
import it.dogs.fivenine.service.UserConnectionService;
import it.dogs.fivenine.service.NotificationService;
import it.dogs.fivenine.exception.ResourceNotFoundException;
import it.dogs.fivenine.exception.InvalidOperationException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserConnectionServiceImpl implements UserConnectionService {

    private final UserConnectionRepository connectionRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;

    public UserConnectionServiceImpl(UserConnectionRepository connectionRepository,
                                   UserRepository userRepository,
                                   NotificationService notificationService) {
        this.connectionRepository = connectionRepository;
        this.userRepository = userRepository;
        this.notificationService = notificationService;
    }

    @Override
    @Transactional
    public void sendConnectionRequest(Long requesterId, ConnectionRequestDTO dto) {
        User requester = userRepository.findById(requesterId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        
        User addressee = userRepository.findById(dto.getTargetUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Target user not found"));

        if (requesterId.equals(dto.getTargetUserId())) {
            throw new InvalidOperationException("Cannot send connection request to yourself");
        }

        // Check if connection already exists
        Optional<UserConnection> existingConnection = connectionRepository
                .findByRequesterAndAddressee(requester, addressee);
        
        if (existingConnection.isPresent()) {
            throw new InvalidOperationException("Connection request already exists");
        }

        // Check reverse connection
        Optional<UserConnection> reverseConnection = connectionRepository
                .findByRequesterAndAddressee(addressee, requester);
        
        if (reverseConnection.isPresent()) {
            throw new InvalidOperationException("Connection already exists or pending");
        }

        UserConnection connection = new UserConnection();
        connection.setRequester(requester);
        connection.setAddressee(addressee);
        connection.setStatus(ConnectionStatus.PENDING);
        connection.setCreatedAt(LocalDateTime.now());

        connectionRepository.save(connection);

        // Create notification
        notificationService.createConnectionRequestNotification(addressee, requester);
    }

    @Override
    @Transactional
    public void acceptConnectionRequest(Long connectionId, Long userId) {
        UserConnection connection = connectionRepository.findById(connectionId)
                .orElseThrow(() -> new ResourceNotFoundException("Connection request not found"));

        if (!connection.getAddressee().getId().equals(userId)) {
            throw new InvalidOperationException("You can only accept requests sent to you");
        }

        if (connection.getStatus() != ConnectionStatus.PENDING) {
            throw new InvalidOperationException("Connection request is not pending");
        }

        connection.setStatus(ConnectionStatus.ACCEPTED);
        connection.setAcceptedAt(LocalDateTime.now());
        connectionRepository.save(connection);

        // Create notification
        notificationService.createConnectionAcceptedNotification(connection.getRequester(), connection.getAddressee());
    }

    @Override
    @Transactional
    public void rejectConnectionRequest(Long connectionId, Long userId) {
        UserConnection connection = connectionRepository.findById(connectionId)
                .orElseThrow(() -> new ResourceNotFoundException("Connection request not found"));

        if (!connection.getAddressee().getId().equals(userId)) {
            throw new InvalidOperationException("You can only reject requests sent to you");
        }

        if (connection.getStatus() != ConnectionStatus.PENDING) {
            throw new InvalidOperationException("Connection request is not pending");
        }

        connection.setStatus(ConnectionStatus.REJECTED);
        connectionRepository.save(connection);
    }

    @Override
    @Transactional
    public void blockUser(Long blockerId, Long blockedUserId) {
        User blocker = userRepository.findById(blockerId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        
        User blocked = userRepository.findById(blockedUserId)
                .orElseThrow(() -> new ResourceNotFoundException("User to block not found"));

        if (blockerId.equals(blockedUserId)) {
            throw new InvalidOperationException("Cannot block yourself");
        }

        // Find existing connection if any
        Optional<UserConnection> connection = connectionRepository
                .findByRequesterAndAddressee(blocker, blocked);
        
        if (connection.isEmpty()) {
            connection = connectionRepository.findByRequesterAndAddressee(blocked, blocker);
        }

        if (connection.isPresent()) {
            connection.get().setStatus(ConnectionStatus.BLOCKED);
            connectionRepository.save(connection.get());
        } else {
            // Create new blocked connection
            UserConnection blockedConnection = new UserConnection();
            blockedConnection.setRequester(blocker);
            blockedConnection.setAddressee(blocked);
            blockedConnection.setStatus(ConnectionStatus.BLOCKED);
            connectionRepository.save(blockedConnection);
        }
    }

    @Override
    @Transactional
    public void removeConnection(Long connectionId, Long userId) {
        UserConnection connection = connectionRepository.findById(connectionId)
                .orElseThrow(() -> new ResourceNotFoundException("Connection not found"));

        if (!connection.getRequester().getId().equals(userId) && 
            !connection.getAddressee().getId().equals(userId)) {
            throw new InvalidOperationException("You can only remove your own connections");
        }

        connectionRepository.delete(connection);
    }

    @Override
    public List<UserConnection> getUserConnections(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        
        return connectionRepository.findByUserAndStatus(user, ConnectionStatus.ACCEPTED);
    }

    @Override
    public List<UserConnection> getPendingRequests(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        
        return connectionRepository.findPendingRequestsForUser(user);
    }

    @Override
    public long countPendingRequests(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        
        return connectionRepository.countPendingRequestsForUser(user);
    }

    @Override
    public boolean areUsersConnected(Long userId1, Long userId2) {
        User user1 = userRepository.findById(userId1)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        User user2 = userRepository.findById(userId2)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        
        return connectionRepository.findAcceptedConnectionBetween(user1, user2).isPresent();
    }

    @Override
    public ConnectionStatus getConnectionStatus(Long userId1, Long userId2) {
        User user1 = userRepository.findById(userId1)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        User user2 = userRepository.findById(userId2)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        
        Optional<UserConnection> connection = connectionRepository.findByRequesterAndAddressee(user1, user2);
        if (connection.isEmpty()) {
            connection = connectionRepository.findByRequesterAndAddressee(user2, user1);
        }
        
        return connection.map(UserConnection::getStatus).orElse(null);
    }

    @Override
    public List<User> getConnectedUsers(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        
        List<UserConnection> connections = connectionRepository.findByUserAndStatus(user, ConnectionStatus.ACCEPTED);
        
        return connections.stream()
                .map(conn -> conn.getRequester().getId().equals(userId) ? conn.getAddressee() : conn.getRequester())
                .collect(Collectors.toList());
    }
}