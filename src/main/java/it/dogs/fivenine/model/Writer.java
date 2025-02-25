package it.dogs.fivenine.model;


import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
@Entity
@Table(name = "writers")
public class Writer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;

    private String firstName;
    private String lastName;
    private Date birthday;

    @OneToMany
    private Set<Movie> movies;
}
