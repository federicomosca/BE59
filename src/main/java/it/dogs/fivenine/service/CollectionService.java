package it.dogs.fivenine.service;

import java.util.List;

import it.dogs.fivenine.model.domain.Collection;
import it.dogs.fivenine.model.domain.sets.CollectionType;
import it.dogs.fivenine.model.dto.CollectionDTOs.CollectionDTO;

public interface CollectionService {
    void createCollection(CollectionDTO dto, Long userId);
    CollectionDTO getCollectionById(Long id);
    void updateCollection(Long id, CollectionDTO dto);
    List<Collection> listAllCollections();
    List<Collection> listUserCollections(Long userId);
    List<Collection> getUserCollectionsByType(Long userId, CollectionType type);
    void deleteCollection(Long id);
    void addMovieToCollection(Long collectionId, Long movieId);
    void removeMovieFromCollection(Long collectionId, Long movieId);
    void addMovieToWatchlist(Long userId, Long movieId);
    void addMovieToFavourites(Long userId, Long movieId);
    void removeMovieFromWatchlist(Long userId, Long movieId);
    void removeMovieFromFavourites(Long userId, Long movieId);
}
