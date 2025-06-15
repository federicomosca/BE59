package it.dogs.fivenine.model.dto.MovieDTOs;

import java.time.LocalDate;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AddMovieDTO {
    
    private String title;

    private String director;

    private String genre;

    private LocalDate releaseDate;
}
