package com.cinema.application.service;

import com.cinema.application.dto.BuyingTicketsDTO;
import com.cinema.application.exceptions.ValidatorException;
import com.cinema.application.validator.impl.SeanceDtoValidator;
import com.cinema.domain.model.Seance;
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
    private final SeanceDtoValidator seanceDtoValidator;
    /**
     * @role Role.ADMIN
     **/
    public Optional<Seance> findOne(Long id) {
        if (id == null) {
            throw new AppException("FIND ONE SEANCE EXCEPTION");
        }
        return Optional.of(seanceRepository
                .findOne(id)
                .orElseThrow());
    }
    /**
     * @role Role.ADMIN
     **/
    public List<Seance> findAll() {
        return seanceRepository
                .findAll();
    }
    /**
     * @role Role.ADMIN
     **/
    public Optional<Seance> add(Seance seance) {
        if (seance == null) {
            throw new AppException("SEANCE ADD SEANCE EXCEPTION");
        }
        seanceDtoValidator.validate(seance);

        if (seanceDtoValidator.hasErrors()) {
            throw new ValidatorException(seanceDtoValidator.getExceptionMessage());
        }
        return seanceRepository
                .save(seance);
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
     Optional<Seance> findByPlaceTitleDate(BuyingTicketsDTO buyingTicketsDTO) {
        if (buyingTicketsDTO == null) {
            throw new AppException("BUYING TICKET DTO IS NULL EXCEPTION");
        }
        return Optional.of(seanceRepository
                .findAll()
                .stream()
                .filter(x -> x.getMovie().getTitle().equals(buyingTicketsDTO.getMovieName()) &
                        x.getPlace().getName().equals(buyingTicketsDTO.getCityName()) &
                        x.getStartOfSeance().equals(buyingTicketsDTO.getStartOfSeance()))
                .findFirst()
                .orElseThrow(() -> new AppException("THERE IS NO SUCH SEANCE")));

    }

}
