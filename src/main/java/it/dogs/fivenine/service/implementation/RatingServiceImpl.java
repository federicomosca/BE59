package it.dogs.fivenine.service.implementation;

import it.dogs.fivenine.exception.ResourceNotFoundException;
import it.dogs.fivenine.model.domain.Movie;
import it.dogs.fivenine.model.domain.Rating;
import it.dogs.fivenine.model.domain.User;
import it.dogs.fivenine.model.dto.RatingDTOs.RatingDTO;
import it.dogs.fivenine.model.dto.RatingDTOs.RatingResponseDTO;
import it.dogs.fivenine.repository.MovieRepository;
import it.dogs.fivenine.repository.RatingRepository;
import it.dogs.fivenine.repository.UserRepository;
import it.dogs.fivenine.service.RatingService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class RatingServiceImpl implements RatingService {
    
    private final RatingRepository ratingRepository;
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;
    
    public RatingServiceImpl(RatingRepository ratingRepository, 
                           UserRepository userRepository,
                           MovieRepository movieRepository) {
        this.ratingRepository = ratingRepository;
        this.userRepository = userRepository;
        this.movieRepository = movieRepository;
    }
    
    @Override
    public Rating createOrUpdateRating(Long userId, Long movieId, RatingDTO ratingDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found with id: " + movieId));
        
        Optional<Rating> existingRating = ratingRepository.findByUserAndMovie(user, movie);
        
        Rating rating;
        if (existingRating.isPresent()) {
            rating = existingRating.get();
            rating.setRating(ratingDTO.getRating());
            rating.setReview(ratingDTO.getReview());
        } else {
            rating = new Rating(user, movie, ratingDTO.getRating(), ratingDTO.getReview());
        }
        
        return ratingRepository.save(rating);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<Rating> getUserRatingForMovie(Long userId, Long movieId) {
        return ratingRepository.findByUserIdAndMovieId(userId, movieId);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<RatingResponseDTO> getMovieRatings(Long movieId) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found with id: " + movieId));
        
        return ratingRepository.findByMovieOrderByCreatedAtDesc(movie)
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<RatingResponseDTO> getMovieRatings(Long movieId, Pageable pageable) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found with id: " + movieId));
        
        return ratingRepository.findByMovie(movie, pageable)
                .map(this::convertToResponseDTO);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<RatingResponseDTO> getUserRatings(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        
        return ratingRepository.findByUser(user)
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<RatingResponseDTO> getUserRatings(Long userId, Pageable pageable) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        
        return ratingRepository.findByUser(user, pageable)
                .map(this::convertToResponseDTO);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Double getAverageRating(Long movieId) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found with id: " + movieId));
        
        Double average = ratingRepository.findAverageRatingByMovie(movie);
        return average != null ? Math.round(average * 10.0) / 10.0 : 0.0;
    }
    
    @Override
    @Transactional(readOnly = true)
    public Long getRatingCount(Long movieId) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found with id: " + movieId));
        
        return ratingRepository.countRatingsByMovie(movie);
    }
    
    @Override
    public void deleteRating(Long userId, Long movieId) {
        Optional<Rating> rating = ratingRepository.findByUserIdAndMovieId(userId, movieId);
        if (rating.isPresent()) {
            ratingRepository.delete(rating.get());
        } else {
            throw new ResourceNotFoundException("Rating not found for user " + userId + " and movie " + movieId);
        }
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean hasUserRatedMovie(Long userId, Long movieId) {
        return ratingRepository.findByUserIdAndMovieId(userId, movieId).isPresent();
    }
    
    private RatingResponseDTO convertToResponseDTO(Rating rating) {
        return new RatingResponseDTO(
                rating.getId(),
                rating.getUser().getUsername(),
                rating.getRating(),
                rating.getReview(),
                rating.getCreatedAt(),
                rating.getUpdatedAt()
        );
    }
}