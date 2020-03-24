package com.cinema.application.service;

import com.cinema.application.dto.TicketDTO;
import com.cinema.application.dto.UserDTO;
import com.cinema.application.dto.mapers.Mapper;
import com.cinema.domain.repository.TicketRepository;
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
public class TicketService {
    private final TicketRepository ticketRepository;

    public Optional<TicketDTO> findOne(Long id) {
        if (id == null) {
            throw new AppException("FIND ONE TICKET EXCEPTION");
        }
        return Optional.of(Mapper.fromTicketToTicketDTO(ticketRepository
                .findOne(id)
                .orElseThrow()));
    }

    public List<TicketDTO> findAll() {
        return ticketRepository
                .findAll()
                .stream()
                .map(Mapper::fromTicketToTicketDTO)
                .collect(Collectors.toList());
    }

    public Optional<TicketDTO> add(TicketDTO ticketDTO) {
        if (ticketDTO == null) {
            throw new AppException("TICKET DTO ADD TICKET EXCEPTION");
        }
        return Optional.of(Mapper.fromTicketToTicketDTO(ticketRepository
                .save(Mapper.fromTicketDTOtoTicket(ticketDTO))
                .orElseThrow()));
    }

    public void delete(Long id) {
        if (id == null) {
            throw new AppException("DELETE TICKET ID EXCEPTION");
        }
        ticketRepository.delete(id);
    }
}
