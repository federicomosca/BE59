package it.dogs.fivenine.model.domain;

import lombok.Data;
import jakarta.persistence.*;
import java.util.List;

import it.dogs.fivenine.model.domain.sets.CollectionType;

@Data
@Entity
@Table(name = "collections")
public class Collection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private CollectionType type;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToMany
    @JoinTable(name = "collection_movie", joinColumns = @JoinColumn(name = "collection_id"), inverseJoinColumns = @JoinColumn(name = "movie_id"))
    private List<Movie> movies;

}