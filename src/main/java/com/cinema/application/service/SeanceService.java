package com.cinema.application.service;

import com.cinema.application.dto.SeanceDTO;
import com.cinema.application.dto.UserDTO;
import com.cinema.application.dto.mapers.Mapper;
import com.cinema.domain.repository.SeanceRepository;
import com.cinema.infrastructure.exceptions.AppException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class SeanceService {
    private final SeanceRepository seanceRepository;

    public Optional<SeanceDTO> findOne(Long id) {
        if (id == null) {
            throw new AppException("FIND ONE SEANCE EXCEPTION");
        }
        return Optional.of(Mapper.fromSeanceToSeanceDTO(seanceRepository
                .findOne(id)
                .orElseThrow()));
    }

    public List<SeanceDTO> findAll() {
        return seanceRepository
                .findAll()
                .stream()
                .map(Mapper::fromSeanceToSeanceDTO)
                .collect(Collectors.toList());
    }

    public Optional<SeanceDTO> add(SeanceDTO seanceDTO) {
        if (seanceDTO == null) {
            throw new AppException("SEANCE ADD SEANCE EXCEPTION");
        }
        return Optional.of(Mapper.fromSeanceToSeanceDTO(seanceRepository
                .save(Mapper.fromSeanceDTOtoSeance(seanceDTO))
                .orElseThrow()));
    }

    public void delete(Long id) {
        if (id == null) {
            throw new AppException("DELETE SEANCE ID EXCEPTION");
        }
        seanceRepository.delete(id);
    }

}
