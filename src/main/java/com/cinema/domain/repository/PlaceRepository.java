package com.cinema.domain.repository;

import com.cinema.domain.model.Place;
import com.cinema.domain.repository.generic.GenericRepository;

import java.util.Optional;

public interface PlaceRepository extends GenericRepository<Place, Long> {
    Optional<Place> findByName(String name);
}
