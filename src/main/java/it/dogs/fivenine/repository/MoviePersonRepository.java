package it.dogs.fivenine.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.dogs.fivenine.model.domain.MoviePerson;

@Repository
public interface MoviePersonRepository extends JpaRepository<MoviePerson, Long>{
    
    List<MoviePerson> findByMovieId(Long movieId);
    List<MoviePerson> findByPersonId(Long personId);
    List<MoviePerson> findByMovieIdAndRole(Long movieId, MoviePerson.Role role);
}