package com.cinema.application.validator.impl;

import com.cinema.application.dto.FilteringMoviesDTO;
import com.cinema.application.dto.enums.FilteringOption;
import com.cinema.application.validator.AbstractValidator;
import com.cinema.domain.model.enums.Genre;
import com.cinema.infrastructure.exceptions.AppException;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;

@Component
public class FilteringMovieDtoValidator extends AbstractValidator<FilteringMoviesDTO> {
    @Override
    public Map<String, String> validate(FilteringMoviesDTO item) {

        errors.clear();
        if (isOption(item)) {
            errors.put("VALIDATION EXCEPTION", "FILTERING OPTION NO MATCH");
        }
        if (item.getValue().isEmpty() || item.getValue() == null) {
            errors.put("VALIDATION EXCEPTION", "NULL OR EMPTY STRING");
        }
        if (isDuration(item)) {
            errors.put("VALIDATION EXCEPTION","VALUE IS NOT INTEGER");
        }
        if (isGenre(item)) {
            errors.put("VALIDATION EXCEPTION","VALUE IS NOT VALID ENUM TYPE");
        }
        return errors;
    }

    private boolean isOption(FilteringMoviesDTO item) {
        return item.getOption() == null || Arrays
                .stream(FilteringOption.values())
                .noneMatch(option -> option.equals(item.getOption()));
    }

    private boolean isDuration(FilteringMoviesDTO item) {
        return item.getOption()
                .equals(FilteringOption.DURATION) &&
                !item.getValue().matches("[0-9]*");
    }

    private boolean isGenre(FilteringMoviesDTO item) {
        return item.getOption().equals(FilteringOption.GENRE) && Arrays.stream(Genre.values())
                .map(Enum::toString)
                .noneMatch(genre -> genre.equals(item.getValue()));
    }
}
