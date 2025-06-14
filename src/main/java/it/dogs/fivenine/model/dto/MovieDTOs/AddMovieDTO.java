package it.dogs.fivenine.model.dto.MovieDTOs;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AddMovieDTO {
    
    private String title;

    private String genre;

    private Date releaseDate;
}
