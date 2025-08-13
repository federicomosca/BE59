package it.dogs.fivenine.model.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "collection_shares")
public class CollectionShare {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "collection_id", nullable = false)
    private Collection collection;

    @ManyToOne
    @JoinColumn(name = "shared_by_id", nullable = false)
    private User sharedBy; // Chi condivide

    @ManyToOne
    @JoinColumn(name = "shared_with_id", nullable = false)
    private User sharedWith; // Con chi è condivisa

    @Column(columnDefinition = "TEXT")
    private String message; // Messaggio opzionale

    @Column(name = "shared_at", nullable = false)
    private LocalDateTime sharedAt = LocalDateTime.now();

    @Column(name = "viewed_at")
    private LocalDateTime viewedAt;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Collection getCollection() {
        return collection;
    }

    public void setCollection(Collection collection) {
        this.collection = collection;
    }

    public User getSharedBy() {
        return sharedBy;
    }

    public void setSharedBy(User sharedBy) {
        this.sharedBy = sharedBy;
    }

    public User getSharedWith() {
        return sharedWith;
    }

    public void setSharedWith(User sharedWith) {
        this.sharedWith = sharedWith;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getSharedAt() {
        return sharedAt;
    }

    public void setSharedAt(LocalDateTime sharedAt) {
        this.sharedAt = sharedAt;
    }

    public LocalDateTime getViewedAt() {
        return viewedAt;
    }

    public void setViewedAt(LocalDateTime viewedAt) {
        this.viewedAt = viewedAt;
    }

    public boolean isViewed() {
        return viewedAt != null;
    }
}