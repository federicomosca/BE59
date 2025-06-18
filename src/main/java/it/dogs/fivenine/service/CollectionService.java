package it.dogs.fivenine.service;

import java.util.List;

import it.dogs.fivenine.model.domain.Collection;
import it.dogs.fivenine.model.dto.CollectionDTOs.CollectionDTO;
import it.dogs.fivenine.model.dto.UserDTOs.LoginDTO;

public interface CollectionService {
    void createCollection(CollectionDTO dto);
    CollectionDTO getCollectionById(Long id);
    void updateCollection(Long id, CollectionDTO dto);
    List<Collection> listAllCollections();
    List<Collection> listUserCollections(LoginDTO dto)
;}
