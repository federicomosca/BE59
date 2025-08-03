package it.dogs.fivenine.service;

import it.dogs.fivenine.model.domain.MovieRequest;
import it.dogs.fivenine.model.dto.MovieDTOs.MovieRequestDTO;
import it.dogs.fivenine.model.result.MovieRequestResult;

import java.util.List;

public interface MovieRequestService {
    
    MovieRequestResult submitMovieRequest(Long userId, String username, MovieRequestDTO dto);
    List<MovieRequest> getPendingRequests();
    List<MovieRequest> getAllRequests();
    List<MovieRequest> getUserRequests(Long userId);
    MovieRequest approveRequest(Long requestId, Long adminUserId);
    MovieRequest rejectRequest(Long requestId, Long adminUserId, String reason);
}