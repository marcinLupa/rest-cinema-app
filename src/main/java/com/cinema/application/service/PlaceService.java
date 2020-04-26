package com.cinema.application.service;

import com.cinema.application.exceptions.ValidatorException;
import com.cinema.application.validator.impl.PlaceDtoValidator;
import com.cinema.domain.model.Place;
import com.cinema.domain.repository.PlaceRepository;
import com.cinema.infrastructure.exceptions.AppException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PlaceService {

    private final PlaceRepository placeRepository;
    private final PlaceDtoValidator placeDtoValidator;

    /**
     * @role Role.ADMIN
     **/
    public Optional<Place> findOne(Long id) {
        if (id == null) {
            throw new AppException("FIND ONE PLACE EXCEPTION");
        }
        return placeRepository
                .findOne(id);

    }

    /**
     * @role Role.ADMIN
     **/
    public List<Place> findAll() {
        return placeRepository
                .findAll();
    }
        /**
         * @role Role.ADMIN
         **/
        public Optional<Place> add (Place place){
            if (place == null) {
                throw new AppException("PLACE DTO ADD EXCEPTION");
            }
            placeDtoValidator.validate(place);
            if (placeDtoValidator.hasErrors()) {
                throw new ValidatorException(placeDtoValidator.getExceptionMessage());
            }

            return Optional.of(placeRepository
                    .save(place)
                    .orElseThrow());
        }

        /**
         * @role Role.ADMIN
         **/
        public void delete (Long id){
            if (id == null) {
                throw new AppException("DELETE PLACE ID EXCEPTION");
            }
            placeRepository.delete(id);
        }

    }
