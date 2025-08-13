package it.dogs.fivenine.service.implementation;

import it.dogs.fivenine.model.domain.PrivateMessage;
import it.dogs.fivenine.model.domain.User;
import it.dogs.fivenine.model.dto.MessageDTOs.PrivateMessageDTO;
import it.dogs.fivenine.repository.PrivateMessageRepository;
import it.dogs.fivenine.repository.UserRepository;
import it.dogs.fivenine.service.PrivateMessageService;
import it.dogs.fivenine.service.NotificationService;
import it.dogs.fivenine.exception.ResourceNotFoundException;
import it.dogs.fivenine.exception.InvalidOperationException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PrivateMessageServiceImpl implements PrivateMessageService {

    private final PrivateMessageRepository messageRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;

    public PrivateMessageServiceImpl(PrivateMessageRepository messageRepository,
                                   UserRepository userRepository,
                                   NotificationService notificationService) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.notificationService = notificationService;
    }

    @Override
    @Transactional
    public void sendMessage(Long senderId, PrivateMessageDTO dto) {
        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new ResourceNotFoundException("Sender not found"));
        
        User recipient = userRepository.findById(dto.getRecipientId())
                .orElseThrow(() -> new ResourceNotFoundException("Recipient not found"));

        if (senderId.equals(dto.getRecipientId())) {
            throw new InvalidOperationException("Cannot send message to yourself");
        }

        PrivateMessage message = new PrivateMessage();
        message.setSender(sender);
        message.setRecipient(recipient);
        message.setSubject(dto.getSubject());
        message.setContent(dto.getContent());
        message.setSentAt(LocalDateTime.now());

        messageRepository.save(message);

        // Create notification
        notificationService.createNewMessageNotification(recipient, sender);
    }

    @Override
    public List<PrivateMessage> getInboxMessages(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        
        return messageRepository.findInboxMessages(user);
    }

    @Override
    public List<PrivateMessage> getSentMessages(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        
        return messageRepository.findSentMessages(user);
    }

    @Override
    public List<PrivateMessage> getConversation(Long userId1, Long userId2) {
        User user1 = userRepository.findById(userId1)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        User user2 = userRepository.findById(userId2)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        
        return messageRepository.findConversationBetween(user1, user2);
    }

    @Override
    public long countUnreadMessages(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        
        return messageRepository.countUnreadMessages(user);
    }

    @Override
    @Transactional
    public void markAsRead(Long messageId, Long userId) {
        PrivateMessage message = messageRepository.findById(messageId)
                .orElseThrow(() -> new ResourceNotFoundException("Message not found"));

        if (!message.getRecipient().getId().equals(userId)) {
            throw new InvalidOperationException("You can only mark your own messages as read");
        }

        if (message.getReadAt() == null) {
            message.setReadAt(LocalDateTime.now());
            messageRepository.save(message);
        }
    }

    @Override
    @Transactional
    public void deleteMessage(Long messageId, Long userId, boolean fromInbox) {
        PrivateMessage message = messageRepository.findById(messageId)
                .orElseThrow(() -> new ResourceNotFoundException("Message not found"));

        if (fromInbox && !message.getRecipient().getId().equals(userId)) {
            throw new InvalidOperationException("You can only delete your own messages");
        }
        
        if (!fromInbox && !message.getSender().getId().equals(userId)) {
            throw new InvalidOperationException("You can only delete your own messages");
        }

        if (fromInbox) {
            message.setDeletedByRecipient(true);
        } else {
            message.setDeletedBySender(true);
        }

        // If both parties deleted, remove from database
        if (message.isDeletedBySender() && message.isDeletedByRecipient()) {
            messageRepository.delete(message);
        } else {
            messageRepository.save(message);
        }
    }

    @Override
    public PrivateMessage getMessage(Long messageId, Long userId) {
        PrivateMessage message = messageRepository.findById(messageId)
                .orElseThrow(() -> new ResourceNotFoundException("Message not found"));

        if (!message.getSender().getId().equals(userId) && 
            !message.getRecipient().getId().equals(userId)) {
            throw new InvalidOperationException("You can only access your own messages");
        }

        return message;
    }
}