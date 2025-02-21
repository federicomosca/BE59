package it.dogs.fivenine.services;

import it.dogs.fivenine.entities.Collection;
import it.dogs.fivenine.repositories.CollectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CollectionService {

    @Autowired
    private CollectionRepository collectionRepository;

    public Collection createCollection(Collection collection) {
        return collectionRepository.save(collection);
    }

    public Collection getCollectionById(Long id) {
        return collectionRepository.findById(id).orElse(null);
    }

    // ... other methods
}
