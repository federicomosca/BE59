package it.dogs.fivenine.service.implementation;

import it.dogs.fivenine.model.domain.MovieRequest;
import it.dogs.fivenine.model.dto.MovieDTOs.MovieRequestDTO;
import it.dogs.fivenine.model.result.MovieRequestResult;
import it.dogs.fivenine.repository.MovieRequestRepository;
import it.dogs.fivenine.service.AuditService;
import it.dogs.fivenine.service.MovieRequestService;

import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MovieRequestServiceImpl implements MovieRequestService {

    private final MovieRequestRepository repository;
    private final AuditService auditService;

    public MovieRequestServiceImpl(MovieRequestRepository repository, AuditService auditService) {
        this.repository = repository;
        this.auditService = auditService;
    }

    @Override
    public MovieRequestResult submitMovieRequest(Long userId, String username, MovieRequestDTO dto) {
        try {
            MovieRequest request = new MovieRequest();
            
            // Set user information
            request.setRequestedByUserId(userId);
            request.setRequestedByUsername(username);
            request.setRequestDate(LocalDateTime.now());
            request.setStatus(MovieRequest.RequestStatus.PENDING);
            request.setWantsAttribution(dto.getWantsAttribution());
            
            // Set movie information from DTO
            request.setTitle(dto.getTitle());
            request.setOriginalTitle(dto.getOriginalTitle());
            request.setReleaseYear(dto.getReleaseYear());
            request.setDirector(dto.getDirector());
            request.setMainActors(dto.getMainActors());
            request.setGenres(dto.getGenres());
            request.setPlotSummary(dto.getPlotSummary());
            request.setImdbId(dto.getImdbId());
            request.setAdditionalInfo(dto.getAdditionalInfo());
            
            MovieRequest savedRequest = repository.save(request);
            
            // Log the movie request
            auditService.logUserAction(userId, "MOVIE_REQUEST_SUBMITTED", null, null, 
                "Movie: " + dto.getTitle() + " (" + dto.getReleaseYear() + ")", true);
            
            return MovieRequestResult.success(savedRequest.getId());
            
        } catch (Exception e) {
            auditService.logUserAction(userId, "MOVIE_REQUEST_FAILED", null, null, 
                "Error: " + e.getMessage(), false);
            return MovieRequestResult.failure("SUBMISSION_ERROR", "Failed to submit request: " + e.getMessage());
        }
    }

    @Override
    public List<MovieRequest> getPendingRequests() {
        return repository.findByStatusOrderByRequestDateAsc(MovieRequest.RequestStatus.PENDING);
    }

    @Override
    public List<MovieRequest> getAllRequests() {
        return repository.findAllByOrderByRequestDateDesc();
    }

    @Override
    public List<MovieRequest> getUserRequests(Long userId) {
        return repository.findByRequestedByUserIdOrderByRequestDateDesc(userId);
    }

    @Override
    public MovieRequest approveRequest(Long requestId, Long adminUserId) {
        MovieRequest request = repository.findById(requestId)
            .orElseThrow(() -> new RuntimeException("Request not found"));
        
        request.setStatus(MovieRequest.RequestStatus.APPROVED);
        request.setAdminUserId(adminUserId);
        request.setHandledDate(LocalDateTime.now());
        
        MovieRequest savedRequest = repository.save(request);
        
        auditService.logUserAction(adminUserId, "MOVIE_REQUEST_APPROVED", null, null, 
            "Request ID: " + requestId + ", Movie: " + request.getTitle(), true);
        
        return savedRequest;
    }

    @Override
    public MovieRequest rejectRequest(Long requestId, Long adminUserId, String reason) {
        MovieRequest request = repository.findById(requestId)
            .orElseThrow(() -> new RuntimeException("Request not found"));
        
        request.setStatus(MovieRequest.RequestStatus.REJECTED);
        request.setAdminUserId(adminUserId);
        request.setHandledDate(LocalDateTime.now());
        request.setRejectionReason(reason);
        
        MovieRequest savedRequest = repository.save(request);
        
        auditService.logUserAction(adminUserId, "MOVIE_REQUEST_REJECTED", null, null, 
            "Request ID: " + requestId + ", Reason: " + reason, true);
        
        return savedRequest;
    }
}