package com.cinema.infrastructure.repository.jpa;

import com.cinema.domain.model.Seance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface JpaSeanceRepository extends JpaRepository<Seance,Long> {
    Optional<Seance> findByStartOfSeanceAndMovieTitleAndAndPlace_Name(LocalDateTime startOfSeance, String MovieTitle, String place);
}
