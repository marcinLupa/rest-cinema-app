package com.cinema.application.validator;

import com.cinema.application.dto.MovieDTO;
import com.cinema.domain.model.Movie;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

@Component
public class MovieValidator extends AbstractValidator<MovieDTO> {

    @Override
    public Map<String, String> validate(MovieDTO item) {
        errors.clear();

        if (Objects.isNull(item)) {
            errors.put("object", "null");
        }

        if (!isTitle(item)) {
            errors.put("title", "......");
        }

        return errors;
    }

    private boolean isTitle(MovieDTO movieDTO) {
        return false;
    }
}
