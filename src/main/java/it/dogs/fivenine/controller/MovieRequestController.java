package it.dogs.fivenine.controller;

import it.dogs.fivenine.model.domain.MovieRequest;
import it.dogs.fivenine.model.dto.MovieDTOs.MovieRequestDTO;
import it.dogs.fivenine.model.result.MovieRequestResult;
import it.dogs.fivenine.service.MovieRequestService;
import it.dogs.fivenine.util.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movie-requests")
public class MovieRequestController {

    @Autowired
    private MovieRequestService movieRequestService;
    
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<MovieRequestResult> submitMovieRequest(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody MovieRequestDTO dto) {
        
        // Extract JWT token (assuming "Bearer token" format)
        String token = authHeader.substring(7);
        Long userId = jwtUtil.getUserIdFromToken(token);
        String username = jwtUtil.getUsernameFromToken(token);
        
        MovieRequestResult result = movieRequestService.submitMovieRequest(userId, username, dto);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/my-requests")
    public ResponseEntity<List<MovieRequest>> getMyRequests(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        Long userId = jwtUtil.getUserIdFromToken(token);
        
        List<MovieRequest> requests = movieRequestService.getUserRequests(userId);
        return ResponseEntity.ok(requests);
    }

    // Admin endpoints
    @GetMapping("/pending")
    public ResponseEntity<List<MovieRequest>> getPendingRequests() {
        List<MovieRequest> requests = movieRequestService.getPendingRequests();
        return ResponseEntity.ok(requests);
    }

    @GetMapping("/all")
    public ResponseEntity<List<MovieRequest>> getAllRequests() {
        List<MovieRequest> requests = movieRequestService.getAllRequests();
        return ResponseEntity.ok(requests);
    }

    @PostMapping("/{requestId}/approve")
    public ResponseEntity<MovieRequest> approveRequest(
            @PathVariable Long requestId,
            @RequestHeader("Authorization") String authHeader) {
        
        String token = authHeader.substring(7);
        Long adminUserId = jwtUtil.getUserIdFromToken(token);
        
        MovieRequest request = movieRequestService.approveRequest(requestId, adminUserId);
        return ResponseEntity.ok(request);
    }

    @PostMapping("/{requestId}/reject")
    public ResponseEntity<MovieRequest> rejectRequest(
            @PathVariable Long requestId,
            @RequestParam String reason,
            @RequestHeader("Authorization") String authHeader) {
        
        String token = authHeader.substring(7);
        Long adminUserId = jwtUtil.getUserIdFromToken(token);
        
        MovieRequest request = movieRequestService.rejectRequest(requestId, adminUserId, reason);
        return ResponseEntity.ok(request);
    }
}