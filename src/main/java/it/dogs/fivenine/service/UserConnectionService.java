package it.dogs.fivenine.service;

import it.dogs.fivenine.model.domain.User;
import it.dogs.fivenine.model.domain.UserConnection;
import it.dogs.fivenine.model.domain.enums.ConnectionStatus;
import it.dogs.fivenine.model.dto.ConnectionDTOs.ConnectionRequestDTO;

import java.util.List;

public interface UserConnectionService {
    
    void sendConnectionRequest(Long requesterId, ConnectionRequestDTO dto);
    
    void acceptConnectionRequest(Long connectionId, Long userId);
    
    void rejectConnectionRequest(Long connectionId, Long userId);
    
    void blockUser(Long blockerId, Long blockedUserId);
    
    void removeConnection(Long connectionId, Long userId);
    
    List<UserConnection> getUserConnections(Long userId);
    
    List<UserConnection> getPendingRequests(Long userId);
    
    long countPendingRequests(Long userId);
    
    boolean areUsersConnected(Long userId1, Long userId2);
    
    ConnectionStatus getConnectionStatus(Long userId1, Long userId2);
    
    List<User> getConnectedUsers(Long userId);
}