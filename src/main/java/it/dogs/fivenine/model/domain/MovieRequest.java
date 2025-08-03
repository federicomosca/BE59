package it.dogs.fivenine.model.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "movie_requests")
public class MovieRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Request Information
    private Long requestedByUserId;
    private String requestedByUsername;
    private LocalDateTime requestDate;
    
    @Enumerated(EnumType.STRING)
    private RequestStatus status; // PENDING, APPROVED, REJECTED, COMPLETED
    
    private Boolean wantsAttribution; // Does user want "Addition requested by" tag?

    // Movie Information (from user request)
    private String title;
    private String originalTitle;
    private String releaseYear;
    private String director;
    private String mainActors;
    private String genres;
    private String plotSummary;
    private String imdbId; // Optional if user provides
    
    @Column(length = 1000)
    private String additionalInfo; // Any extra info user wants to provide

    // Admin fields
    private Long adminUserId; // Who handled the request
    private LocalDateTime handledDate;
    @Column(length = 1000)
    private String adminNotes;
    private String rejectionReason;
    
    // Link to created movie (if approved and added)
    @OneToOne
    @JoinColumn(name = "created_movie_id")
    private Movie createdMovie;

    public enum RequestStatus {
        PENDING, APPROVED, REJECTED, COMPLETED
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getRequestedByUserId() {
        return requestedByUserId;
    }
    public void setRequestedByUserId(Long requestedByUserId) {
        this.requestedByUserId = requestedByUserId;
    }
    public String getRequestedByUsername() {
        return requestedByUsername;
    }
    public void setRequestedByUsername(String requestedByUsername) {
        this.requestedByUsername = requestedByUsername;
    }
    public LocalDateTime getRequestDate() {
        return requestDate;
    }
    public void setRequestDate(LocalDateTime requestDate) {
        this.requestDate = requestDate;
    }
    public RequestStatus getStatus() {
        return status;
    }
    public void setStatus(RequestStatus status) {
        this.status = status;
    }
    public Boolean getWantsAttribution() {
        return wantsAttribution;
    }
    public void setWantsAttribution(Boolean wantsAttribution) {
        this.wantsAttribution = wantsAttribution;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getOriginalTitle() {
        return originalTitle;
    }
    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }
    public String getReleaseYear() {
        return releaseYear;
    }
    public void setReleaseYear(String releaseYear) {
        this.releaseYear = releaseYear;
    }
    public String getDirector() {
        return director;
    }
    public void setDirector(String director) {
        this.director = director;
    }
    public String getMainActors() {
        return mainActors;
    }
    public void setMainActors(String mainActors) {
        this.mainActors = mainActors;
    }
    public String getGenres() {
        return genres;
    }
    public void setGenres(String genres) {
        this.genres = genres;
    }
    public String getPlotSummary() {
        return plotSummary;
    }
    public void setPlotSummary(String plotSummary) {
        this.plotSummary = plotSummary;
    }
    public String getImdbId() {
        return imdbId;
    }
    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }
    public String getAdditionalInfo() {
        return additionalInfo;
    }
    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }
    public Long getAdminUserId() {
        return adminUserId;
    }
    public void setAdminUserId(Long adminUserId) {
        this.adminUserId = adminUserId;
    }
    public LocalDateTime getHandledDate() {
        return handledDate;
    }
    public void setHandledDate(LocalDateTime handledDate) {
        this.handledDate = handledDate;
    }
    public String getAdminNotes() {
        return adminNotes;
    }
    public void setAdminNotes(String adminNotes) {
        this.adminNotes = adminNotes;
    }
    public String getRejectionReason() {
        return rejectionReason;
    }
    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }
    public Movie getCreatedMovie() {
        return createdMovie;
    }
    public void setCreatedMovie(Movie createdMovie) {
        this.createdMovie = createdMovie;
    }
}