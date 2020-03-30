package com.cinema.infrastructure.repository.jpa;

import com.cinema.domain.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaMovieRepository extends JpaRepository<Movie, Long> {
    Movie findByTitle(String title);

}
