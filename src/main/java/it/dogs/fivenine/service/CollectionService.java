package it.dogs.fivenine.service;

import it.dogs.fivenine.model.Collection;

public interface CollectionService {

    Collection createCollection(Collection collection);
    Collection getCollectionById(Long id);
}
