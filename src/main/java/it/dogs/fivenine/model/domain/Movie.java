package it.dogs.fivenine.model.domain;

import lombok.Data;
import lombok.Getter;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Data
@Entity
@Table(name = "movies")
@Getter
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column
    private String genre;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    @ManyToOne
    private Director director;

    @ManyToOne
    private Writer writer;

    @ManyToMany
    @JoinTable(name = "starring", joinColumns = @JoinColumn(name = "movie_id"), inverseJoinColumns = @JoinColumn(name = "actor_id"))
    private Set<Actor> actors;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
