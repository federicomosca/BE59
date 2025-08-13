package it.dogs.fivenine.controller;

import it.dogs.fivenine.model.domain.MovieRecommendation;
import it.dogs.fivenine.model.dto.RecommendationDTOs.MovieRecommendationDTO;
import it.dogs.fivenine.service.MovieRecommendationService;
import it.dogs.fivenine.util.AuthenticationUtils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/recommendations")
public class MovieRecommendationController {

    private final MovieRecommendationService recommendationService;
    private final AuthenticationUtils authUtils;

    public MovieRecommendationController(MovieRecommendationService recommendationService, AuthenticationUtils authUtils) {
        this.recommendationService = recommendationService;
        this.authUtils = authUtils;
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> recommendMovie(
            @Valid @RequestBody MovieRecommendationDTO dto, HttpServletRequest request) {
        Long userId = authUtils.getUserIdFromRequest(request);
        if (userId != null) {
            recommendationService.recommendMovie(userId, dto);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Movie recommendation sent successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/received")
    public ResponseEntity<List<MovieRecommendation>> getReceivedRecommendations(HttpServletRequest request) {
        Long userId = authUtils.getUserIdFromRequest(request);
        if (userId != null) {
            List<MovieRecommendation> recommendations = recommendationService.getReceivedRecommendations(userId);
            return ResponseEntity.ok(recommendations);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/sent")
    public ResponseEntity<List<MovieRecommendation>> getSentRecommendations(HttpServletRequest request) {
        Long userId = authUtils.getUserIdFromRequest(request);
        if (userId != null) {
            List<MovieRecommendation> recommendations = recommendationService.getSentRecommendations(userId);
            return ResponseEntity.ok(recommendations);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/unviewed/count")
    public ResponseEntity<Map<String, Long>> getUnviewedCount(HttpServletRequest request) {
        Long userId = authUtils.getUserIdFromRequest(request);
        if (userId != null) {
            long count = recommendationService.countUnviewedRecommendations(userId);
            Map<String, Long> response = new HashMap<>();
            response.put("count", count);
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping("/{recommendationId}/viewed")
    public ResponseEntity<Map<String, String>> markAsViewed(
            @PathVariable Long recommendationId, HttpServletRequest request) {
        Long userId = authUtils.getUserIdFromRequest(request);
        if (userId != null) {
            recommendationService.markAsViewed(recommendationId, userId);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Recommendation marked as viewed");
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping("/{recommendationId}/dismiss")
    public ResponseEntity<Map<String, String>> dismissRecommendation(
            @PathVariable Long recommendationId, HttpServletRequest request) {
        Long userId = authUtils.getUserIdFromRequest(request);
        if (userId != null) {
            recommendationService.dismissRecommendation(recommendationId, userId);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Recommendation dismissed");
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}