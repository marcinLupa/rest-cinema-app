package com.cinema.application.service;

import com.cinema.application.dto.*;
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
    private final SeanceService seanceService;
    private final PlaceService placeService;
    private final MovieService movieService;



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

    public List<TicketDTO> buyingTickets(BuyingTicketsDTO buyingTicketsDTO) {
//        System.out.println(buyingTicketsDTO);
//
//            if (buyingTicketsDTO == null) {
//                throw new AppException("BUYING TICKET DTO IS NULL EXCEPTION");
//            }
//            PlaceDTO placeSearch = placeService
//                    .findByName(buyingTicketsDTO.getCityName())
//                    .orElseThrow(() -> new AppException("CITY FROM USER OUT OF BASE"));
//
//            System.out.println(placeSearch);
//
//        MovieDTO movieSearch = movieService
//                .findByTitle(buyingTicketsDTO.getMovieName())
//                .orElseThrow(() -> new AppException("MOVIE FROM USER OUT OF BASE"));
//        System.out.println(movieSearch);

        System.out.println(seanceService.findByPlaceTitleDate(buyingTicketsDTO));

        return List.of();
    }
}
