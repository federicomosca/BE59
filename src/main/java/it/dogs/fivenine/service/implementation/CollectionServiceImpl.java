package it.dogs.fivenine.service.implementation;

import it.dogs.fivenine.model.domain.Collection;
import it.dogs.fivenine.model.dto.CollectionDTOs.CollectionDTO;
import it.dogs.fivenine.model.dto.UserDTOs.LoginDTO;
import it.dogs.fivenine.service.CollectionService;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class CollectionServiceImpl implements CollectionService {




    @Override
    public void createCollection(CollectionDTO dto) {

    }

    @Override
    public CollectionDTO getCollectionById(Long id) {
        return null;
    }

    @Override
    public void updateCollection(Long id, CollectionDTO dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateCollection'");
    }

    @Override
    public List<Collection> listAllCollections() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listAllCollections'");
    }

    @Override
    public List<Collection> listUserCollections(LoginDTO dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listUserCollections'");
    }

    
}
