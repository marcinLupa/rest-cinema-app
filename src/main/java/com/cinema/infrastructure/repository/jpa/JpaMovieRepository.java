package com.cinema.infrastructure.repository.jpa;

import com.cinema.domain.model.Movie;
import com.cinema.domain.model.enums.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaMovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findAllByTitle(String title);

    List<Movie> findAllByDurationBefore(Integer duration);

    List<Movie> findAllByGenre(Genre genre);

}
