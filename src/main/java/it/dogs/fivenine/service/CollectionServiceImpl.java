package it.dogs.fivenine.service;

import it.dogs.fivenine.model.domain.Collection;
import it.dogs.fivenine.repository.CollectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CollectionServiceImpl implements CollectionService {

    private CollectionRepository collectionRepository;

    @Autowired
    public CollectionServiceImpl(CollectionRepository collectionRepository) {
        this.collectionRepository = collectionRepository;
    }

    public Collection createCollection(Collection collection) {
        return collectionRepository.save(collection);
    }

    public Collection getCollectionById(Long id) {
        return collectionRepository.findById(id).orElse(null);
    }

    // ... other methods
}
