package com.cinema.infrastructure.repository.impl;

import com.cinema.domain.model.Movie;
import com.cinema.domain.model.Place;
import com.cinema.domain.repository.PlaceRepository;
import com.cinema.infrastructure.repository.jpa.JpaPlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
@Component
@RequiredArgsConstructor
public class PlaceRepositoryImpl implements PlaceRepository {

    private final JpaPlaceRepository jpaPlaceRepository;

    @Override
    public Optional<Place> findOne(Long id) {
        return jpaPlaceRepository.findById(id);
    }

    @Override
    public List<Place> findAll() {
        return jpaPlaceRepository.findAll();
    }

    @Override
    public Optional<Place> add(Place place) {
        return Optional.of(jpaPlaceRepository.save(place));
    }

    @Override
    public void delete(Long id) {
        jpaPlaceRepository.deleteById(id);
    }
}
