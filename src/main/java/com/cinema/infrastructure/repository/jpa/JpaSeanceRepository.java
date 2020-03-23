package com.cinema.infrastructure.repository.jpa;

import com.cinema.domain.model.Seance;
import com.cinema.domain.repository.SeanceRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaSeanceRepository extends JpaRepository<Seance,Long> {
}
