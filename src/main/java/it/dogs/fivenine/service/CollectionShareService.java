package it.dogs.fivenine.service;

import it.dogs.fivenine.model.domain.CollectionShare;
import it.dogs.fivenine.model.dto.ShareDTOs.CollectionShareDTO;

import java.util.List;

public interface CollectionShareService {
    
    void shareCollection(Long sharerId, CollectionShareDTO dto);
    
    List<CollectionShare> getReceivedShares(Long userId);
    
    List<CollectionShare> getSentShares(Long userId);
    
    long countUnviewedShares(Long userId);
    
    void markAsViewed(Long shareId, Long userId);
}