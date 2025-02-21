package it.dogs.fivenine.controllers;

import it.dogs.fivenine.entities.Collection;
import it.dogs.fivenine.services.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/collections")
public class CollectionController {

    @Autowired
    private CollectionService collectionService;

    @PostMapping
    public Collection createCollection(@RequestBody Collection collection) {
        return collectionService.createCollection(collection);
    }

    @GetMapping("/{id}")
    public Collection getCollectionById(@PathVariable Long id) {
        return collectionService.getCollectionById(id);
    }

    // ... other endpoints
}
