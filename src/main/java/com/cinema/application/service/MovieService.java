package com.cinema.application.service;

import com.cinema.application.dto.FilteringMoviesDTO;
import com.cinema.application.exceptions.MovieServiceException;
import com.cinema.application.validator.impl.FilteringMovieDtoValidator;
import com.cinema.application.validator.impl.MovieValidator;
import com.cinema.domain.model.Movie;
import com.cinema.domain.model.enums.Genre;
import com.cinema.domain.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;
    private final FilteringMovieDtoValidator filteringValidator;
    private final MovieValidator validator;


    /**
     * @role Role.ADMIN
     **/

    public Optional<Movie> findOne(Long id) {
        if (id == null) {
            throw new MovieServiceException("FIND ONE MOVIE EXCEPTION");
        }
        return movieRepository
                .findOne(id);
    }

    /**
     * @role Role.ADMIN
     **/
    public List<Movie> findAll() {
        return movieRepository
                .findAll();
    }

    /**
     * @role Role.ADMIN
     **/
    public Optional<Movie> add(Movie movie) {
        validator.validate(movie);
        if (validator.hasErrors()) {
            throw new MovieServiceException(validator.getExceptionMessage());
        }

        return Optional.of(movieRepository
                .save(movie)
                .orElseThrow());
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
    public List<Movie> getMovies(FilteringMoviesDTO filteringMoviesDTO) {

        filteringValidator.validate(filteringMoviesDTO);

        if (filteringValidator.hasErrors()) {

            throw new MovieServiceException(filteringValidator.getExceptionMessage());
        }
        List<Movie> filtratedMovies = new ArrayList<>();

        switch (filteringMoviesDTO.getOption()) {

            case TITLE -> filtratedMovies = movieRepository
                    .findByTitle(filteringMoviesDTO.getValue());

            case DURATION -> filtratedMovies = movieRepository
                    .findAllByDurationBefore(Integer.valueOf(filteringMoviesDTO.getValue()));

            case GENRE -> filtratedMovies = movieRepository
                    .findAllByGenre(Genre.valueOf(filteringMoviesDTO.getValue()));
        }
        return filtratedMovies;
    }

}
