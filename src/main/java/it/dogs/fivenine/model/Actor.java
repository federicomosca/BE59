package it.dogs.fivenine.model;

import java.util.Date;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "actors")
public class Actor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    
    @Column
    private String firstName;
    
    @Column
    private String lastName;
    
    @Column
    private Date birthday;
    
    @Column
    private String nationality;
    
    @ManyToMany(mappedBy = "actors")
    private Set<Movie> movies;
	
}
