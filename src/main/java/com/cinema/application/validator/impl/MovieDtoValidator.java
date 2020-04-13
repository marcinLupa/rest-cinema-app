package com.cinema.application.validator.impl;

import com.cinema.application.dto.MovieDTO;
import com.cinema.application.exceptions.MovieServiceException;
import com.cinema.application.service.MovieService;
import com.cinema.application.validator.AbstractValidator;
import com.cinema.domain.model.Movie;
import com.cinema.domain.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class MovieDtoValidator extends AbstractValidator<MovieDTO> {

    private final MovieRepository movieRepository;
    @Override
    public Map<String, String> validate(MovieDTO item) {
        errors.clear();

        if (Objects.isNull(item)) {
            errors.put("MOVIE VALIDATOR", "MOVIE DTO IS NULL");
        }

        if (!isOneByTitle(item.getTitle())) {
            errors.put("MOVIE VALIDATOR", "MOVIE WITH GIVEN TITLE ALREADY EXISTS");
        }

        return errors;
    }

    private boolean isOneByTitle(String title) {
        if (title == null) {
            throw new MovieServiceException("FIND BY TITLE EXCEPTION");
        }
        return movieRepository
                .findByTitle(title).isEmpty();
    }
}
