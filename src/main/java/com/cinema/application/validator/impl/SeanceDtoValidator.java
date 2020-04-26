package com.cinema.application.validator.impl;

import com.cinema.application.exceptions.ValidatorException;
import com.cinema.application.validator.AbstractValidator;
import com.cinema.domain.model.Seance;
import com.cinema.domain.repository.SeanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class SeanceDtoValidator extends AbstractValidator<Seance> {

    private final SeanceRepository seanceRepository;

    private final PlaceDtoValidator placeDtoValidator;

    private final MovieValidator movieValidator;

    @Override
    public Map<String, String> validate(Seance item) {
        errors.clear();


        if (Objects.isNull(item)) {
            errors.put("PLACE VALIDATOR", "PLACE DTO IS NULL");
        }

        placeDtoValidator.validate(item.getPlace());

        if (placeDtoValidator.hasErrors()) {
            throw new ValidatorException(placeDtoValidator.getExceptionMessage());
        }
        movieValidator.validate(item.getMovie());

        if (movieValidator.hasErrors()) {
            throw new ValidatorException(movieValidator.getExceptionMessage());
        }
        if (!isOne(item)) {
            errors.put("SEANCE VALIDATOR", "SEANCE WITH GIVEN TITLE ALREADY EXISTS");
        }

        return errors;
    }

    private boolean isOne(Seance item) {

        return seanceRepository
                .findBySeance(item).isEmpty();
    }
}
