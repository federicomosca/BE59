package it.dogs.fivenine.repository;

import it.dogs.fivenine.model.domain.MovieRecommendation;
import it.dogs.fivenine.model.domain.User;
import it.dogs.fivenine.model.domain.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRecommendationRepository extends JpaRepository<MovieRecommendation, Long> {
    
    List<MovieRecommendation> findByRecipientAndIsDismissedOrderByCreatedAtDesc(User recipient, boolean isDismissed);
    
    List<MovieRecommendation> findByRecommenderOrderByCreatedAtDesc(User recommender);
    
    @Query("SELECT COUNT(mr) FROM MovieRecommendation mr WHERE mr.recipient = :user AND mr.viewedAt IS NULL AND mr.isDismissed = false")
    long countUnviewedRecommendations(@Param("user") User user);
    
    @Query("SELECT mr FROM MovieRecommendation mr WHERE mr.recipient = :user AND mr.viewedAt IS NULL AND mr.isDismissed = false ORDER BY mr.createdAt DESC")
    List<MovieRecommendation> findUnviewedRecommendations(@Param("user") User user);
    
    Optional<MovieRecommendation> findByRecommenderAndRecipientAndMovie(User recommender, User recipient, Movie movie);
}