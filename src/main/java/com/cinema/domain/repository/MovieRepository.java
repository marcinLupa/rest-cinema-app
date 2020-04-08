package com.cinema.domain.repository;

import com.cinema.domain.model.Movie;
import com.cinema.domain.model.enums.Genre;
import com.cinema.domain.repository.generic.GenericRepository;

import java.util.List;
import java.util.Optional;

public interface MovieRepository extends GenericRepository<Movie, Long> {
    List<Movie> findByTitle(String title);

    List<Movie> findAllByGenre(Genre genre);

    List<Movie> findAllByDurationBefore(Integer duration);

}
