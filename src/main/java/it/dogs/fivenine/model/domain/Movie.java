package it.dogs.fivenine.model.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Basic Information
    private String title;
    private String originalTitle;
    private LocalDate releaseDate;
    private Integer runtimeMinutes;
    private String genres; // Comma-separated for now, can be normalized later
    private String countries; // Comma-separated
    private String languages; // Comma-separated
    private String ageRating; // PG, PG-13, R, etc.

    // Story & Content
    @Column(length = 2000)
    private String plotSummary;
    private String tagline;

    // Production Information
    private BigDecimal budget;
    private BigDecimal boxOfficeRevenue;
    private String productionCompanies; // Comma-separated
    private String distributors; // Comma-separated
    private String filmingLocations; // Comma-separated

    // Technical Information
    private String colorType; // COLOR, BLACK_AND_WHITE, MIXED
    private String aspectRatio; // 16:9, 2.35:1, etc.
    private String soundMix; // Stereo, Dolby Digital, etc.
    private String filmFormat; // 35mm, Digital, IMAX, etc.

    // Metadata
    private String imdbId;
    private String requestedBy; // Username who requested this movie
    private LocalDateTime addedDate;
    @Column(length = 1000)
    private String adminNotes;

    // Relationships
    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MoviePerson> moviePersons;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
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
    public LocalDate getReleaseDate() {
        return releaseDate;
    }
    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }
    public Integer getRuntimeMinutes() {
        return runtimeMinutes;
    }
    public void setRuntimeMinutes(Integer runtimeMinutes) {
        this.runtimeMinutes = runtimeMinutes;
    }
    public String getGenres() {
        return genres;
    }
    public void setGenres(String genres) {
        this.genres = genres;
    }
    public String getCountries() {
        return countries;
    }
    public void setCountries(String countries) {
        this.countries = countries;
    }
    public String getLanguages() {
        return languages;
    }
    public void setLanguages(String languages) {
        this.languages = languages;
    }
    public String getAgeRating() {
        return ageRating;
    }
    public void setAgeRating(String ageRating) {
        this.ageRating = ageRating;
    }
    public String getPlotSummary() {
        return plotSummary;
    }
    public void setPlotSummary(String plotSummary) {
        this.plotSummary = plotSummary;
    }
    public String getTagline() {
        return tagline;
    }
    public void setTagline(String tagline) {
        this.tagline = tagline;
    }
    public BigDecimal getBudget() {
        return budget;
    }
    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }
    public BigDecimal getBoxOfficeRevenue() {
        return boxOfficeRevenue;
    }
    public void setBoxOfficeRevenue(BigDecimal boxOfficeRevenue) {
        this.boxOfficeRevenue = boxOfficeRevenue;
    }
    public String getProductionCompanies() {
        return productionCompanies;
    }
    public void setProductionCompanies(String productionCompanies) {
        this.productionCompanies = productionCompanies;
    }
    public String getDistributors() {
        return distributors;
    }
    public void setDistributors(String distributors) {
        this.distributors = distributors;
    }
    public String getFilmingLocations() {
        return filmingLocations;
    }
    public void setFilmingLocations(String filmingLocations) {
        this.filmingLocations = filmingLocations;
    }
    public String getColorType() {
        return colorType;
    }
    public void setColorType(String colorType) {
        this.colorType = colorType;
    }
    public String getAspectRatio() {
        return aspectRatio;
    }
    public void setAspectRatio(String aspectRatio) {
        this.aspectRatio = aspectRatio;
    }
    public String getSoundMix() {
        return soundMix;
    }
    public void setSoundMix(String soundMix) {
        this.soundMix = soundMix;
    }
    public String getFilmFormat() {
        return filmFormat;
    }
    public void setFilmFormat(String filmFormat) {
        this.filmFormat = filmFormat;
    }
    public String getImdbId() {
        return imdbId;
    }
    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }
    public String getRequestedBy() {
        return requestedBy;
    }
    public void setRequestedBy(String requestedBy) {
        this.requestedBy = requestedBy;
    }
    public LocalDateTime getAddedDate() {
        return addedDate;
    }
    public void setAddedDate(LocalDateTime addedDate) {
        this.addedDate = addedDate;
    }
    public String getAdminNotes() {
        return adminNotes;
    }
    public void setAdminNotes(String adminNotes) {
        this.adminNotes = adminNotes;
    }
    public List<MoviePerson> getMoviePersons() {
        return moviePersons;
    }
    public void setMoviePersons(List<MoviePerson> moviePersons) {
        this.moviePersons = moviePersons;
    }
}
