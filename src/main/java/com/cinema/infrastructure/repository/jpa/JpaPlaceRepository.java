package com.cinema.infrastructure.repository.jpa;

import com.cinema.domain.model.Place;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaPlaceRepository extends JpaRepository<Place,Long> {

     Optional<Place> findByName(String name);
}
