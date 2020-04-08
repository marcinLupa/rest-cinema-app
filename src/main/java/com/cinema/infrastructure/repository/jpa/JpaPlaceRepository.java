package com.cinema.infrastructure.repository.jpa;

import com.cinema.domain.model.Place;
import org.springframework.data.jpa.repository.JpaRepository;


public interface JpaPlaceRepository extends JpaRepository<Place, Long> {

}
