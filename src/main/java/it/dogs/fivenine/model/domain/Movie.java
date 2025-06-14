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

    @Column(nullable = false)
    private String title;

    @Column
    private String genre;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
