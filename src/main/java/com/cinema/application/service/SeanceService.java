package com.cinema.application.service;

import com.cinema.application.dto.BuyingTicketsDTO;
import com.cinema.application.dto.SeanceDTO;
import com.cinema.application.dto.UserDTO;
import com.cinema.application.dto.mapers.Mapper;
import com.cinema.domain.repository.SeanceRepository;
import com.cinema.infrastructure.exceptions.AppException;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    public Optional<SeanceDTO> findByPlaceTitleDate(BuyingTicketsDTO buyingTicketsDTO){
        if (buyingTicketsDTO == null) {
            throw new AppException("BUYING TICKET DTO IS NULL EXCEPTION");
        }

   return      Optional.of(Mapper.fromSeanceToSeanceDTO(seanceRepository
                .findAll()
                .stream()
                .filter(x->x.getMovie().getTitle().equals(buyingTicketsDTO.getMovieName())&
                        x.getPlace().getName().equals(buyingTicketsDTO.getCityName())&
                     x.getStartOfSeance().equals(buyingTicketsDTO.getStartOfSeance()))
                .findFirst()
                .orElseThrow(()->new AppException("THERE IS NO SUCH SEANCE"))));

    }

}
