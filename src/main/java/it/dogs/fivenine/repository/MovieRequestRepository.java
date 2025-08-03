package it.dogs.fivenine.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.dogs.fivenine.model.domain.MovieRequest;

@Repository
public interface MovieRequestRepository extends JpaRepository<MovieRequest, Long>{
    
    List<MovieRequest> findByStatus(MovieRequest.RequestStatus status);
    List<MovieRequest> findByRequestedByUserIdOrderByRequestDateDesc(Long userId);
    List<MovieRequest> findAllByOrderByRequestDateDesc();
    List<MovieRequest> findByStatusOrderByRequestDateAsc(MovieRequest.RequestStatus status);
}