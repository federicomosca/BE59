package it.dogs.fivenine.model.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "movie_recommendations")
public class MovieRecommendation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "recommender_id", nullable = false)
    private User recommender; // Chi raccomanda

    @ManyToOne
    @JoinColumn(name = "recipient_id", nullable = false)
    private User recipient; // A chi raccomanda

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie; // Film raccomandato

    @Column(columnDefinition = "TEXT")
    private String message; // Messaggio opzionale

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "viewed_at")
    private LocalDateTime viewedAt;

    @Column(name = "is_dismissed")
    private boolean isDismissed = false;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getRecommender() {
        return recommender;
    }

    public void setRecommender(User recommender) {
        this.recommender = recommender;
    }

    public User getRecipient() {
        return recipient;
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getViewedAt() {
        return viewedAt;
    }

    public void setViewedAt(LocalDateTime viewedAt) {
        this.viewedAt = viewedAt;
    }

    public boolean isDismissed() {
        return isDismissed;
    }

    public void setDismissed(boolean dismissed) {
        isDismissed = dismissed;
    }

    public boolean isViewed() {
        return viewedAt != null;
    }
}