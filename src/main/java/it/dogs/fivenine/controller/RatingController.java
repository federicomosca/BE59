package it.dogs.fivenine.controller;

import it.dogs.fivenine.model.domain.Rating;
import it.dogs.fivenine.model.dto.RatingDTOs.RatingDTO;
import it.dogs.fivenine.model.dto.RatingDTOs.RatingResponseDTO;
import it.dogs.fivenine.service.RatingService;
import it.dogs.fivenine.util.AuthenticationUtils;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/ratings")
public class RatingController {
    
    private final RatingService ratingService;
    private final AuthenticationUtils authUtils;
    
    public RatingController(RatingService ratingService, AuthenticationUtils authUtils) {
        this.ratingService = ratingService;
        this.authUtils = authUtils;
    }
    
    @PostMapping("/movies/{movieId}")
    public ResponseEntity<RatingResponseDTO> rateMovie(
            @PathVariable Long movieId,
            @Valid @RequestBody RatingDTO ratingDTO,
            HttpServletRequest request) {
        
        Long userId = authUtils.getUserIdFromRequest(request);
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        
        Rating rating = ratingService.createOrUpdateRating(userId, movieId, ratingDTO);
        RatingResponseDTO responseDTO = new RatingResponseDTO(
            rating.getId(),
            rating.getUser().getUsername(),
            rating.getRating(),
            rating.getReview(),
            rating.getCreatedAt(),
            rating.getUpdatedAt()
        );
        
        return ResponseEntity.ok(responseDTO);
    }
    
    @GetMapping("/movies/{movieId}")
    public ResponseEntity<Page<RatingResponseDTO>> getMovieRatings(
            @PathVariable Long movieId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
                Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        
        Page<RatingResponseDTO> ratings = ratingService.getMovieRatings(movieId, pageable);
        return ResponseEntity.ok(ratings);
    }
    
    @GetMapping("/movies/{movieId}/summary")
    public ResponseEntity<Map<String, Object>> getMovieRatingSummary(@PathVariable Long movieId) {
        Double averageRating = ratingService.getAverageRating(movieId);
        Long ratingCount = ratingService.getRatingCount(movieId);
        
        Map<String, Object> summary = Map.of(
            "averageRating", averageRating,
            "ratingCount", ratingCount
        );
        
        return ResponseEntity.ok(summary);
    }
    
    @GetMapping("/movies/{movieId}/my-rating")
    public ResponseEntity<RatingResponseDTO> getMyRatingForMovie(
            @PathVariable Long movieId,
            HttpServletRequest request) {
        
        Long userId = authUtils.getUserIdFromRequest(request);
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        
        Optional<Rating> rating = ratingService.getUserRatingForMovie(userId, movieId);
        if (rating.isPresent()) {
            Rating r = rating.get();
            RatingResponseDTO responseDTO = new RatingResponseDTO(
                r.getId(),
                r.getUser().getUsername(),
                r.getRating(),
                r.getReview(),
                r.getCreatedAt(),
                r.getUpdatedAt()
            );
            return ResponseEntity.ok(responseDTO);
        }
        
        return ResponseEntity.notFound().build();
    }
    
    @GetMapping("/my-ratings")
    public ResponseEntity<Page<RatingResponseDTO>> getMyRatings(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir,
            HttpServletRequest request) {
        
        Long userId = authUtils.getUserIdFromRequest(request);
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
                Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        
        Page<RatingResponseDTO> ratings = ratingService.getUserRatings(userId, pageable);
        return ResponseEntity.ok(ratings);
    }
    
    @DeleteMapping("/movies/{movieId}")
    public ResponseEntity<Void> deleteMyRating(
            @PathVariable Long movieId,
            HttpServletRequest request) {
        
        Long userId = authUtils.getUserIdFromRequest(request);
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        
        ratingService.deleteRating(userId, movieId);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/movies/{movieId}/has-rated")
    public ResponseEntity<Map<String, Boolean>> hasUserRatedMovie(
            @PathVariable Long movieId,
            HttpServletRequest request) {
        
        Long userId = authUtils.getUserIdFromRequest(request);
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        
        boolean hasRated = ratingService.hasUserRatedMovie(userId, movieId);
        return ResponseEntity.ok(Map.of("hasRated", hasRated));
    }
}