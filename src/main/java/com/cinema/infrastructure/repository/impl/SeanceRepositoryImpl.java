package com.cinema.infrastructure.repository.impl;

import com.cinema.domain.model.Seance;
import com.cinema.domain.repository.SeanceRepository;
import com.cinema.infrastructure.repository.jpa.JpaPlaceRepository;
import com.cinema.infrastructure.repository.jpa.JpaSeanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor

public class SeanceRepositoryImpl implements SeanceRepository {
    private final JpaSeanceRepository jpaSeanceRepository;

    @Override
    public Optional<Seance> findOne(Long id) {
        return jpaSeanceRepository.findById(id);
    }

    @Override
    public List<Seance> findAll() {
        return jpaSeanceRepository.findAll();
    }

    @Override
    public Optional<Seance> save(Seance seance) {
        return Optional.of(jpaSeanceRepository.save(seance));
    }

    @Override
    public void delete(Long id) {
        jpaSeanceRepository.deleteById(id);
    }

}
