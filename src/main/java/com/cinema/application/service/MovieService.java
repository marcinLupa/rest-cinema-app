package com.cinema.application.service;

import com.cinema.application.dto.FilteringMoviesDTO;
import com.cinema.application.dto.MovieDTO;
import com.cinema.application.dto.enums.FilteringOption;
import com.cinema.application.dto.mapers.Mapper;
import com.cinema.domain.repository.MovieRepository;
import com.cinema.infrastructure.exceptions.AppException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;

    public Optional<MovieDTO> findOne(Long id) {
        if (id == null) {
            throw new AppException("FIND ONE MOVIE EXCEPTION");
        }
        return Optional.of(Mapper.fromMovieToMovieDTO(movieRepository
                .findOne(id)
                .orElseThrow()));
    }

    public List<MovieDTO> findAll() {
        return movieRepository
                .findAll()
                .stream()
                .map(Mapper::fromMovieToMovieDTO)
                .collect(Collectors.toList());
    }

    public Optional<MovieDTO> add(MovieDTO movieDTO) {
        if (movieDTO == null) {
            throw new AppException("MOVIE DTO IS NULL");
        }
        return Optional.of(Mapper.fromMovieToMovieDTO(movieRepository
                .save(Mapper.fromMovieDTOtoMovie(movieDTO))
                .orElseThrow()));
    }

    public void delete(Long id) {
        if (id == null) {
            throw new AppException("DELETE MOVIE ID IS NULL");
        }
        movieRepository.delete(id);
    }

    public List<MovieDTO> filteringMovies(FilteringMoviesDTO filteringMoviesDTO) {
        if (filteringMoviesDTO == null) {
            throw new AppException("FILTERING OPTION IS NULL");
        }
        List<MovieDTO> filtratedMovies =new ArrayList<>();
        switch (filteringMoviesDTO.getOption()) {
            case NAME ->
             filtratedMovies = movieRepository
                        .findAll()
                        .stream()
                        .filter(x -> x.getTitle().equals(filteringMoviesDTO.getMovieDTO().getTitle()))
                        .map(Mapper::fromMovieToMovieDTO)
                        .collect(Collectors.toList());
            case DURATION_LESS_OR_THE_SAME -> filtratedMovies = movieRepository
                    .findAll()
                    .stream()
                    .filter(x -> x.getDuration()<=filteringMoviesDTO.getMovieDTO().getDuration())
                    .map(Mapper::fromMovieToMovieDTO)
                    .collect(Collectors.toList());
            case DURATION_HIGHER -> filtratedMovies = movieRepository
                    .findAll()
                    .stream()
                    .filter(x -> x.getDuration()>filteringMoviesDTO.getMovieDTO().getDuration())
                    .map(Mapper::fromMovieToMovieDTO)
                    .collect(Collectors.toList());
            case GENRE -> filtratedMovies = movieRepository
                    .findAll()
                    .stream()
                    .filter(x -> x.getGenre().equals(filteringMoviesDTO.getMovieDTO().getGenre()))
                    .map(Mapper::fromMovieToMovieDTO)
                    .collect(Collectors.toList());
        }
        return filtratedMovies;
    }

}
