package com.cinema.application.validator.impl;

import com.cinema.application.exceptions.ValidatorException;
import com.cinema.application.validator.AbstractValidator;
import com.cinema.domain.model.Place;
import com.cinema.domain.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class PlaceDtoValidator extends AbstractValidator<Place> {

    private final PlaceRepository placeRepository;
    @Override
    public Map<String, String> validate(Place item) {
        errors.clear();

        if (Objects.isNull(item)) {
            errors.put("PLACE VALIDATOR", "PLACE DTO IS NULL");
        }

        if (!isOneByTitle(item.getName())) {
            errors.put("PLACE VALIDATOR", "PLACE WITH GIVEN TITLE ALREADY EXISTS");
        }

        return errors;
    }

    private boolean isOneByTitle(String name) {
        if (name == null) {
            throw new ValidatorException("FIND BY NAME EXCEPTION");
        }
        return placeRepository
                .findByName(name).isEmpty();
    }


}
