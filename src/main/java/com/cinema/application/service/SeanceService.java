package com.cinema.application.service;

import com.cinema.application.dto.BuyingTicketsDTO;
import com.cinema.application.dto.SeanceDTO;
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
    /**
     * @role Role.ADMIN
     **/
    public Optional<SeanceDTO> findOne(Long id) {
        if (id == null) {
            throw new AppException("FIND ONE SEANCE EXCEPTION");
        }
        return Optional.of(Mapper.fromSeanceToSeanceDTO(seanceRepository
                .findOne(id)
                .orElseThrow()));
    }
    /**
     * @role Role.ADMIN
     **/
    public List<SeanceDTO> findAll() {
        return seanceRepository
                .findAll()
                .stream()
                .map(Mapper::fromSeanceToSeanceDTO)
                .collect(Collectors.toList());
    }
    /**
     * @role Role.ADMIN
     **/
    public Optional<SeanceDTO> add(SeanceDTO seanceDTO) {
        if (seanceDTO == null) {
            throw new AppException("SEANCE ADD SEANCE EXCEPTION");
        }
        return Optional.of(Mapper.fromSeanceToSeanceDTO(seanceRepository
                .save(Mapper.fromSeanceDTOtoSeance(seanceDTO))
                .orElseThrow()));
    }
    /**
     * @role Role.ADMIN
     **/
    public void delete(Long id) {
        if (id == null) {
            throw new AppException("DELETE SEANCE ID EXCEPTION");
        }
        seanceRepository.delete(id);
    }
    /**
     * method only to help ticket service
     **/
     Optional<SeanceDTO> findByPlaceTitleDate(BuyingTicketsDTO buyingTicketsDTO) {
        if (buyingTicketsDTO == null) {
            throw new AppException("BUYING TICKET DTO IS NULL EXCEPTION");
        }
        return Optional.of(Mapper.fromSeanceToSeanceDTO(seanceRepository
                .findAll()
                .stream()
                .filter(x -> x.getMovie().getTitle().equals(buyingTicketsDTO.getMovieName()) &
                        x.getPlace().getName().equals(buyingTicketsDTO.getCityName()) &
                        x.getStartOfSeance().equals(buyingTicketsDTO.getStartOfSeance()))
                .findFirst()
                .orElseThrow(() -> new AppException("THERE IS NO SUCH SEANCE"))));

    }

}
