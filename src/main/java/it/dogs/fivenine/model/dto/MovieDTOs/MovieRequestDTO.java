package it.dogs.fivenine.model.dto.MovieDTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class MovieRequestDTO {
    
    @NotBlank
    @Size(max = 255)
    private String title;
    
    private String originalTitle;
    
    @Size(max = 10)
    private String releaseYear; // Just year as string for flexibility
    
    private String director;
    private String mainActors;
    private String genres;
    
    @Size(max = 2000)
    private String plotSummary;
    
    private String imdbId;
    
    @Size(max = 1000)
    private String additionalInfo;
    
    private Boolean wantsAttribution = false; // Default to no attribution

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
    public Boolean getWantsAttribution() {
        return wantsAttribution;
    }
    public void setWantsAttribution(Boolean wantsAttribution) {
        this.wantsAttribution = wantsAttribution;
    }
}