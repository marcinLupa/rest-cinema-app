package com.cinema.infrastructure.repository.impl;

import com.cinema.domain.model.Movie;
import com.cinema.domain.model.enums.Genre;
import com.cinema.domain.repository.MovieRepository;
import com.cinema.infrastructure.repository.jpa.JpaMovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MovieRepositoryImpl implements MovieRepository {
    private final JpaMovieRepository jpaMovieRepository;

    @Override
    public Optional<Movie> findOne(Long id) {
        return jpaMovieRepository.findById(id);
    }

    @Override
    public List<Movie> findAll() {
        return jpaMovieRepository.findAll();
    }

    @Override
    public Optional<Movie> save(Movie movie) {
        return Optional.of(jpaMovieRepository.save(movie));
    }

    @Override
    public void delete(Long id) {
        jpaMovieRepository.deleteById(id);
    }

    @Override
    public List<Movie> findByTitle(String title) {
        return jpaMovieRepository.findAllByTitle(title);
    }

    @Override
    public List<Movie> findAllByGenre(Genre genre) {
        return jpaMovieRepository.findAllByGenre(genre);
    }

    @Override
    public List<Movie> findAllByDurationBefore(Integer duration) {
        return jpaMovieRepository.findAllByDurationBefore(duration);
    }
}
