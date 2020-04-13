package com.cinema.application.service;

import com.cinema.application.dto.FilteringMoviesDTO;
import com.cinema.application.dto.MovieDTO;
import com.cinema.application.dto.mapers.Mapper;
import com.cinema.application.exceptions.MovieServiceException;
import com.cinema.application.validator.impl.FilteringMovieDtoValidator;
import com.cinema.application.validator.impl.MovieDtoValidator;
import com.cinema.domain.model.enums.Genre;
import com.cinema.domain.repository.MovieRepository;
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
    private final FilteringMovieDtoValidator filteringValidator;
    private final MovieDtoValidator validator;


    /**
     * @role Role.ADMIN
     **/

    public Optional<MovieDTO> findOne(Long id) {
        if (id == null) {
            throw new MovieServiceException("FIND ONE MOVIE EXCEPTION");
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
        validator.validate(movieDTO);

        if (validator.hasErrors()) {
            throw new MovieServiceException(validator.getExceptionMessage());
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
            throw new MovieServiceException("DELETE MOVIE ID IS NULL");
        }
        movieRepository.delete(id);
    }


    /**
     * Method getMovies ->
     *
     * @param filteringMoviesDTO have fields cityName, movieName, startOfSeance,email, ticketQuantity, discounts
     * @return returns tickets that are committed to date base
     * - finding movies by e-mail title, duration and genre
     * @role Role.USER
     */
    public List<MovieDTO> getMovies(FilteringMoviesDTO filteringMoviesDTO) {

        filteringValidator.validate(filteringMoviesDTO);

        if (filteringValidator.hasErrors()) {

            throw new MovieServiceException(filteringValidator.getExceptionMessage());
        }
        List<MovieDTO> filtratedMovies = new ArrayList<>();

        switch (filteringMoviesDTO.getOption()) {

            case TITLE -> filtratedMovies = movieRepository
                    .findByTitle(filteringMoviesDTO.getValue())
                    .stream()
                    .map(Mapper::fromMovieToMovieDTO)
                    .collect(Collectors.toList());

            case DURATION -> filtratedMovies = movieRepository
                    .findAllByDurationBefore(Integer.valueOf(filteringMoviesDTO.getValue()))
                    .stream()
                    .map(Mapper::fromMovieToMovieDTO)
                    .collect(Collectors.toList());

            case GENRE -> filtratedMovies = movieRepository
                    .findAllByGenre(Genre.valueOf(filteringMoviesDTO.getValue()))
                    .stream()
                    .map(Mapper::fromMovieToMovieDTO)
                    .collect(Collectors.toList());
        }
        return filtratedMovies;
    }

}
