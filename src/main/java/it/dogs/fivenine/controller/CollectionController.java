package it.dogs.fivenine.controller;

import it.dogs.fivenine.model.domain.Collection;
import it.dogs.fivenine.service.CollectionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/collections")
public class CollectionController {

    @Autowired
    private CollectionService collectionService;
}
