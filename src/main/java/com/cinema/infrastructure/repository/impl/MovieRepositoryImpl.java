package com.cinema.infrastructure.repository.impl;

import com.cinema.domain.model.Movie;
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
    public Optional<Movie> add(Movie movie) {
        return Optional.of(jpaMovieRepository.save(movie));
    }

    @Override
    public void delete(Long id) {
        jpaMovieRepository.deleteById(id);
    }
}
