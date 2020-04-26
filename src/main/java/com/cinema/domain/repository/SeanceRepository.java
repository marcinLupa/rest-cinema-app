package com.cinema.domain.repository;

import com.cinema.domain.model.Seance;
import com.cinema.domain.repository.generic.GenericRepository;

import java.util.Optional;

public interface SeanceRepository extends GenericRepository<Seance, Long> {
    Optional<Seance>findBySeance(Seance seance);
}
