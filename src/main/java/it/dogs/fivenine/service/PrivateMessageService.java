package it.dogs.fivenine.service;

import it.dogs.fivenine.model.domain.PrivateMessage;
import it.dogs.fivenine.model.dto.MessageDTOs.PrivateMessageDTO;

import java.util.List;

public interface PrivateMessageService {
    
    void sendMessage(Long senderId, PrivateMessageDTO dto);
    
    List<PrivateMessage> getInboxMessages(Long userId);
    
    List<PrivateMessage> getSentMessages(Long userId);
    
    List<PrivateMessage> getConversation(Long userId1, Long userId2);
    
    long countUnreadMessages(Long userId);
    
    void markAsRead(Long messageId, Long userId);
    
    void deleteMessage(Long messageId, Long userId, boolean fromInbox);
    
    PrivateMessage getMessage(Long messageId, Long userId);
}