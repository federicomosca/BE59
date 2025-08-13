package it.dogs.fivenine.service.implementation;

import it.dogs.fivenine.model.domain.MovieRecommendation;
import it.dogs.fivenine.model.domain.User;
import it.dogs.fivenine.model.domain.Movie;
import it.dogs.fivenine.model.dto.RecommendationDTOs.MovieRecommendationDTO;
import it.dogs.fivenine.repository.MovieRecommendationRepository;
import it.dogs.fivenine.repository.UserRepository;
import it.dogs.fivenine.repository.MovieRepository;
import it.dogs.fivenine.service.MovieRecommendationService;
import it.dogs.fivenine.service.NotificationService;
import it.dogs.fivenine.service.UserConnectionService;
import it.dogs.fivenine.exception.ResourceNotFoundException;
import it.dogs.fivenine.exception.InvalidOperationException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MovieRecommendationServiceImpl implements MovieRecommendationService {

    private final MovieRecommendationRepository recommendationRepository;
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;
    private final NotificationService notificationService;
    private final UserConnectionService connectionService;

    public MovieRecommendationServiceImpl(MovieRecommendationRepository recommendationRepository,
                                        UserRepository userRepository,
                                        MovieRepository movieRepository,
                                        NotificationService notificationService,
                                        UserConnectionService connectionService) {
        this.recommendationRepository = recommendationRepository;
        this.userRepository = userRepository;
        this.movieRepository = movieRepository;
        this.notificationService = notificationService;
        this.connectionService = connectionService;
    }

    @Override
    @Transactional
    public void recommendMovie(Long recommenderId, MovieRecommendationDTO dto) {
        User recommender = userRepository.findById(recommenderId)
                .orElseThrow(() -> new ResourceNotFoundException("Recommender not found"));
        
        User recipient = userRepository.findById(dto.getRecipientId())
                .orElseThrow(() -> new ResourceNotFoundException("Recipient not found"));

        Movie movie = movieRepository.findById(dto.getMovieId())
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found"));

        if (recommenderId.equals(dto.getRecipientId())) {
            throw new InvalidOperationException("Cannot recommend movie to yourself");
        }

        // Check if users are connected
        if (!connectionService.areUsersConnected(recommenderId, dto.getRecipientId())) {
            throw new InvalidOperationException("You can only recommend movies to your connections");
        }

        // Check if already recommended this movie to this user
        Optional<MovieRecommendation> existingRecommendation = recommendationRepository
                .findByRecommenderAndRecipientAndMovie(recommender, recipient, movie);
        
        if (existingRecommendation.isPresent()) {
            throw new InvalidOperationException("You have already recommended this movie to this user");
        }

        MovieRecommendation recommendation = new MovieRecommendation();
        recommendation.setRecommender(recommender);
        recommendation.setRecipient(recipient);
        recommendation.setMovie(movie);
        recommendation.setMessage(dto.getMessage());
        recommendation.setCreatedAt(LocalDateTime.now());

        recommendationRepository.save(recommendation);

        // Create notification
        notificationService.createMovieRecommendationNotification(recipient, recommender, movie);
    }

    @Override
    public List<MovieRecommendation> getReceivedRecommendations(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        
        return recommendationRepository.findByRecipientAndIsDismissedOrderByCreatedAtDesc(user, false);
    }

    @Override
    public List<MovieRecommendation> getSentRecommendations(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        
        return recommendationRepository.findByRecommenderOrderByCreatedAtDesc(user);
    }

    @Override
    public long countUnviewedRecommendations(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        
        return recommendationRepository.countUnviewedRecommendations(user);
    }

    @Override
    @Transactional
    public void markAsViewed(Long recommendationId, Long userId) {
        MovieRecommendation recommendation = recommendationRepository.findById(recommendationId)
                .orElseThrow(() -> new ResourceNotFoundException("Recommendation not found"));

        if (!recommendation.getRecipient().getId().equals(userId)) {
            throw new InvalidOperationException("You can only mark your own recommendations as viewed");
        }

        if (recommendation.getViewedAt() == null) {
            recommendation.setViewedAt(LocalDateTime.now());
            recommendationRepository.save(recommendation);
        }
    }

    @Override
    @Transactional
    public void dismissRecommendation(Long recommendationId, Long userId) {
        MovieRecommendation recommendation = recommendationRepository.findById(recommendationId)
                .orElseThrow(() -> new ResourceNotFoundException("Recommendation not found"));

        if (!recommendation.getRecipient().getId().equals(userId)) {
            throw new InvalidOperationException("You can only dismiss your own recommendations");
        }

        recommendation.setDismissed(true);
        recommendationRepository.save(recommendation);
    }
}