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
    private Long Id;

    @Column
    private String firstName;
    
    @Column
    private String lastName;
    
    @Column
    private Date birthday;

    @OneToMany(mappedBy="writer")
    private Set<Movie> movies;
}
