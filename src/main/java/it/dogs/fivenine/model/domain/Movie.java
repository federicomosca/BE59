package it.dogs.fivenine.model.domain;

import lombok.Data;
import lombok.Getter;
import jakarta.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "movies")
@Getter
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String director;

    private String genre;

    private LocalDate releaseDate;
}
