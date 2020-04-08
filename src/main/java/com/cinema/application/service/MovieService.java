package com.cinema.application.service;

import com.cinema.application.dto.FilteringMoviesDTO;
import com.cinema.application.dto.MovieDTO;
import com.cinema.application.dto.mapers.Mapper;
import com.cinema.domain.model.enums.Genre;
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
    /**
     * @role Role.ADMIN
     **/

    public Optional<MovieDTO> findOne(Long id) {
        if (id == null) {
            throw new AppException("FIND ONE MOVIE EXCEPTION");
        }
        return Optional.of(Mapper.fromMovieToMovieDTO(movieRepository
                .findOne(id)
                .orElseThrow()));
    }
    /**
     * @role Role.ADMIN
     **/
    public List<MovieDTO> findAll() {
        return movieRepository
                .findAll()
                .stream()
                .map(Mapper::fromMovieToMovieDTO)
                .collect(Collectors.toList());
    }
    /**
     * @role Role.ADMIN
     **/
    public Optional<MovieDTO> add(MovieDTO movieDTO) {
        if (movieDTO == null) {
            throw new AppException("MOVIE DTO IS NULL");
        }
        return Optional.of(Mapper.fromMovieToMovieDTO(movieRepository
                .save(Mapper.fromMovieDTOtoMovie(movieDTO))
                .orElseThrow()));
    }
    /**
     * @role Role.ADMIN
     **/
    public void delete(Long id) {
        if (id == null) {
            throw new AppException("DELETE MOVIE ID IS NULL");
        }
        movieRepository.delete(id);
    }
    /**
     * Method getMovies ->
     * @role Role.USER
     * @param filteringMoviesDTO have fields cityName, movieName, startOfSeance,email, ticketQuantity, discounts
     * @return returns tickets that are committed to date base
     * - finding movies by e-mail title, duration and genre
     */
    public List<MovieDTO> getMovies(FilteringMoviesDTO filteringMoviesDTO) {

        if (filteringMoviesDTO == null) {
            throw new AppException("FILTERING OPTION IS NULL");
        }
        List<MovieDTO> filtratedMovies = new ArrayList<>();

        switch (filteringMoviesDTO.getOption()) {

            case TITLE -> filtratedMovies = movieRepository
                    .findByTitle(filteringMoviesDTO.getValue())
                    .stream()
                    .map(Mapper::fromMovieToMovieDTO)
                    .collect(Collectors.toList());

            case DURATION -> {
                if (!filteringMoviesDTO.getValue().matches("[0-9]*")) {
                    throw new AppException("VALUE IS NOT INTEGER");
                }
                filtratedMovies = movieRepository
                        .findAllByDurationBefore(Integer.valueOf(filteringMoviesDTO.getValue()))
                        .stream()
                        .map(Mapper::fromMovieToMovieDTO)
                        .collect(Collectors.toList());
            }

            case GENRE -> {
                if (Arrays.stream(Genre.values())
                        .map(Enum::toString)
                        .noneMatch(genre -> genre.equals(filteringMoviesDTO.getValue()))) {
                    throw new AppException("VALUE IS NOT VALID ENUM TYPE");
                }
                filtratedMovies = movieRepository
                        .findAllByGenre(Genre.valueOf(filteringMoviesDTO.getValue()))
                        .stream()
                        .map(Mapper::fromMovieToMovieDTO)
                        .collect(Collectors.toList());
            }
        }
        return filtratedMovies;
    }

}
