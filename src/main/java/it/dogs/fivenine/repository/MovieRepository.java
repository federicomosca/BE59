package it.dogs.fivenine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.dogs.fivenine.model.domain.Movie;
import java.util.List;
import java.time.LocalDate;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    
    List<Movie> findByTitleContainingIgnoreCase(String title);
    
    List<Movie> findByGenresContainingIgnoreCase(String genre);
    
    List<Movie> findByReleaseDate(LocalDate releaseDate);
    
    List<Movie> findByReleaseDateBetween(LocalDate startDate, LocalDate endDate);
    
    @Query("SELECT DISTINCT m FROM Movie m LEFT JOIN m.moviePersons mp LEFT JOIN mp.person p WHERE " +
           "(?1 IS NULL OR LOWER(m.title) LIKE LOWER(CONCAT('%', ?1, '%'))) AND " +
           "(?2 IS NULL OR LOWER(m.genres) LIKE LOWER(CONCAT('%', ?2, '%'))) AND " +
           "(?3 IS NULL OR (mp.role = 'DIRECTOR' AND LOWER(p.name) LIKE LOWER(CONCAT('%', ?3, '%')))) AND " +
           "(?4 IS NULL OR m.releaseDate >= ?4) AND " +
           "(?5 IS NULL OR m.releaseDate <= ?5)")
    List<Movie> findMoviesWithFilters(@Param("title") String title,
                                    @Param("genre") String genre,
                                    @Param("director") String director,
                                    @Param("fromDate") LocalDate fromDate,
                                    @Param("toDate") LocalDate toDate);
    
    @Query("SELECT DISTINCT m FROM Movie m JOIN m.moviePersons mp JOIN mp.person p WHERE " +
           "mp.role = 'DIRECTOR' AND LOWER(p.name) LIKE LOWER(CONCAT('%', ?1, '%'))")
    List<Movie> findByDirectorNameContainingIgnoreCase(@Param("director") String director);
    
    @Query("SELECT m FROM Movie m ORDER BY m.releaseDate DESC")
    List<Movie> findRecentMovies();
    
    @Query("SELECT m FROM Movie m WHERE m.releaseDate >= :date ORDER BY m.releaseDate DESC")
    List<Movie> findMoviesReleasedAfter(@Param("date") LocalDate date);
}
