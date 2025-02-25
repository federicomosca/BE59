package it.dogs.fivenine.model;

import lombok.Data;
import jakarta.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String genre;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    @ManyToOne
    private Director director;

    @ManyToOne
    private Writer writer;

}
