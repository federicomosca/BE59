package it.dogs.fivenine.controller;

import it.dogs.fivenine.model.domain.CollectionShare;
import it.dogs.fivenine.model.dto.ShareDTOs.CollectionShareDTO;
import it.dogs.fivenine.service.CollectionShareService;
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
@RequestMapping("/collection-shares")
public class CollectionShareController {

    private final CollectionShareService shareService;
    private final AuthenticationUtils authUtils;

    public CollectionShareController(CollectionShareService shareService, AuthenticationUtils authUtils) {
        this.shareService = shareService;
        this.authUtils = authUtils;
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> shareCollection(
            @Valid @RequestBody CollectionShareDTO dto, HttpServletRequest request) {
        Long userId = authUtils.getUserIdFromRequest(request);
        if (userId != null) {
            shareService.shareCollection(userId, dto);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Collection shared successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/received")
    public ResponseEntity<List<CollectionShare>> getReceivedShares(HttpServletRequest request) {
        Long userId = authUtils.getUserIdFromRequest(request);
        if (userId != null) {
            List<CollectionShare> shares = shareService.getReceivedShares(userId);
            return ResponseEntity.ok(shares);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/sent")
    public ResponseEntity<List<CollectionShare>> getSentShares(HttpServletRequest request) {
        Long userId = authUtils.getUserIdFromRequest(request);
        if (userId != null) {
            List<CollectionShare> shares = shareService.getSentShares(userId);
            return ResponseEntity.ok(shares);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/unviewed/count")
    public ResponseEntity<Map<String, Long>> getUnviewedCount(HttpServletRequest request) {
        Long userId = authUtils.getUserIdFromRequest(request);
        if (userId != null) {
            long count = shareService.countUnviewedShares(userId);
            Map<String, Long> response = new HashMap<>();
            response.put("count", count);
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping("/{shareId}/viewed")
    public ResponseEntity<Map<String, String>> markAsViewed(
            @PathVariable Long shareId, HttpServletRequest request) {
        Long userId = authUtils.getUserIdFromRequest(request);
        if (userId != null) {
            shareService.markAsViewed(shareId, userId);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Share marked as viewed");
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}