package it.dogs.fivenine.service.implementation;

import it.dogs.fivenine.model.domain.CollectionShare;
import it.dogs.fivenine.model.domain.User;
import it.dogs.fivenine.model.domain.Collection;
import it.dogs.fivenine.model.domain.enums.CollectionVisibility;
import it.dogs.fivenine.model.dto.ShareDTOs.CollectionShareDTO;
import it.dogs.fivenine.repository.CollectionShareRepository;
import it.dogs.fivenine.repository.UserRepository;
import it.dogs.fivenine.repository.CollectionRepository;
import it.dogs.fivenine.service.CollectionShareService;
import it.dogs.fivenine.service.NotificationService;
import it.dogs.fivenine.service.UserConnectionService;
import it.dogs.fivenine.exception.ResourceNotFoundException;
import it.dogs.fivenine.exception.InvalidOperationException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CollectionShareServiceImpl implements CollectionShareService {

    private final CollectionShareRepository shareRepository;
    private final UserRepository userRepository;
    private final CollectionRepository collectionRepository;
    private final NotificationService notificationService;
    private final UserConnectionService connectionService;

    public CollectionShareServiceImpl(CollectionShareRepository shareRepository,
                                    UserRepository userRepository,
                                    CollectionRepository collectionRepository,
                                    NotificationService notificationService,
                                    UserConnectionService connectionService) {
        this.shareRepository = shareRepository;
        this.userRepository = userRepository;
        this.collectionRepository = collectionRepository;
        this.notificationService = notificationService;
        this.connectionService = connectionService;
    }

    @Override
    @Transactional
    public void shareCollection(Long sharerId, CollectionShareDTO dto) {
        User sharer = userRepository.findById(sharerId)
                .orElseThrow(() -> new ResourceNotFoundException("Sharer not found"));
        
        User recipient = userRepository.findById(dto.getTargetUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Recipient not found"));

        Collection collection = collectionRepository.findById(dto.getCollectionId())
                .orElseThrow(() -> new ResourceNotFoundException("Collection not found"));

        if (sharerId.equals(dto.getTargetUserId())) {
            throw new InvalidOperationException("Cannot share collection with yourself");
        }

        // Check if user owns the collection
        if (!collection.getUser().getId().equals(sharerId)) {
            throw new InvalidOperationException("You can only share your own collections");
        }

        // Check collection visibility and connection status
        if (collection.getVisibility() == CollectionVisibility.PRIVATE) {
            // For private collections, users must be connected
            if (!connectionService.areUsersConnected(sharerId, dto.getTargetUserId())) {
                throw new InvalidOperationException("You can only share private collections with your connections");
            }
        } else if (collection.getVisibility() == CollectionVisibility.FRIENDS) {
            // For friends-only collections, users must be connected
            if (!connectionService.areUsersConnected(sharerId, dto.getTargetUserId())) {
                throw new InvalidOperationException("You can only share friends-only collections with your connections");
            }
        }

        // Check if already shared with this user
        Optional<CollectionShare> existingShare = shareRepository
                .findByCollectionAndSharedByAndSharedWith(collection, sharer, recipient);
        
        if (existingShare.isPresent()) {
            throw new InvalidOperationException("You have already shared this collection with this user");
        }

        CollectionShare share = new CollectionShare();
        share.setCollection(collection);
        share.setSharedBy(sharer);
        share.setSharedWith(recipient);
        share.setMessage(dto.getMessage());
        share.setSharedAt(LocalDateTime.now());

        shareRepository.save(share);

        // Create notification
        notificationService.createCollectionSharedNotification(recipient, sharer, collection);
    }

    @Override
    public List<CollectionShare> getReceivedShares(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        
        return shareRepository.findBySharedWithOrderBySharedAtDesc(user);
    }

    @Override
    public List<CollectionShare> getSentShares(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        
        return shareRepository.findBySharedByOrderBySharedAtDesc(user);
    }

    @Override
    public long countUnviewedShares(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        
        return shareRepository.countUnviewedShares(user);
    }

    @Override
    @Transactional
    public void markAsViewed(Long shareId, Long userId) {
        CollectionShare share = shareRepository.findById(shareId)
                .orElseThrow(() -> new ResourceNotFoundException("Share not found"));

        if (!share.getSharedWith().getId().equals(userId)) {
            throw new InvalidOperationException("You can only mark your own shares as viewed");
        }

        if (share.getViewedAt() == null) {
            share.setViewedAt(LocalDateTime.now());
            shareRepository.save(share);
        }
    }
}