package com.cinema.domain.repository;

import com.cinema.domain.model.Movie;
import com.cinema.domain.repository.generic.GenericRepository;

import java.util.Optional;

public interface MovieRepository extends GenericRepository<Movie, Long> {
    Optional<Movie> findByTitle(String title);
}
