package it.dogs.fivenine.controller;

import it.dogs.fivenine.model.domain.Collection;
import it.dogs.fivenine.model.domain.sets.CollectionType;
import it.dogs.fivenine.model.dto.CollectionDTOs.CollectionDTO;
import it.dogs.fivenine.service.CollectionService;
import it.dogs.fivenine.util.AuthenticationUtils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/collections")
public class CollectionController {

    private final CollectionService collectionService;
    private final AuthenticationUtils authUtils;

    public CollectionController(CollectionService collectionService, AuthenticationUtils authUtils) {
        this.collectionService = collectionService;
        this.authUtils = authUtils;
    }

    @GetMapping
    public ResponseEntity<List<Collection>> getAllCollections() {
        List<Collection> collections = collectionService.listAllCollections();
        return ResponseEntity.ok(collections);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CollectionDTO> getCollectionById(@PathVariable Long id) {
        CollectionDTO collection = collectionService.getCollectionById(id);
        if (collection != null) {
            return ResponseEntity.ok(collection);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Void> createCollection(@RequestBody CollectionDTO collectionDTO, HttpServletRequest request) {
        Long userId = authUtils.getUserIdFromRequest(request);
        if (userId != null) {
            collectionService.createCollection(collectionDTO, userId);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateCollection(@PathVariable Long id, @RequestBody CollectionDTO collectionDTO) {
        collectionService.updateCollection(id, collectionDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCollection(@PathVariable Long id) {
        collectionService.deleteCollection(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/my")
    public ResponseEntity<List<Collection>> getMyCollections(HttpServletRequest request) {
        Long userId = authUtils.getUserIdFromRequest(request);
        if (userId != null) {
            List<Collection> collections = collectionService.listUserCollections(userId);
            return ResponseEntity.ok(collections);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/my/watchlist")
    public ResponseEntity<List<Collection>> getMyWatchlist(HttpServletRequest request) {
        Long userId = authUtils.getUserIdFromRequest(request);
        if (userId != null) {
            List<Collection> collections = collectionService.getUserCollectionsByType(userId, CollectionType.watchlist);
            return ResponseEntity.ok(collections);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/my/favourites")
    public ResponseEntity<List<Collection>> getMyFavourites(HttpServletRequest request) {
        Long userId = authUtils.getUserIdFromRequest(request);
        if (userId != null) {
            List<Collection> collections = collectionService.getUserCollectionsByType(userId, CollectionType.favourites);
            return ResponseEntity.ok(collections);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping("/{collectionId}/movies/{movieId}")
    public ResponseEntity<Void> addMovieToCollection(@PathVariable Long collectionId, @PathVariable Long movieId) {
        collectionService.addMovieToCollection(collectionId, movieId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{collectionId}/movies/{movieId}")
    public ResponseEntity<Void> removeMovieFromCollection(@PathVariable Long collectionId, @PathVariable Long movieId) {
        collectionService.removeMovieFromCollection(collectionId, movieId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/watchlist/movies/{movieId}")
    public ResponseEntity<Void> addMovieToWatchlist(@PathVariable Long movieId, HttpServletRequest request) {
        Long userId = authUtils.getUserIdFromRequest(request);
        if (userId != null) {
            collectionService.addMovieToWatchlist(userId, movieId);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping("/favourites/movies/{movieId}")
    public ResponseEntity<Void> addMovieToFavourites(@PathVariable Long movieId, HttpServletRequest request) {
        Long userId = authUtils.getUserIdFromRequest(request);
        if (userId != null) {
            collectionService.addMovieToFavourites(userId, movieId);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @DeleteMapping("/watchlist/movies/{movieId}")
    public ResponseEntity<Void> removeMovieFromWatchlist(@PathVariable Long movieId, HttpServletRequest request) {
        Long userId = authUtils.getUserIdFromRequest(request);
        if (userId != null) {
            collectionService.removeMovieFromWatchlist(userId, movieId);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @DeleteMapping("/favourites/movies/{movieId}")
    public ResponseEntity<Void> removeMovieFromFavourites(@PathVariable Long movieId, HttpServletRequest request) {
        Long userId = authUtils.getUserIdFromRequest(request);
        if (userId != null) {
            collectionService.removeMovieFromFavourites(userId, movieId);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

}
