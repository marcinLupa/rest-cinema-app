package com.cinema.application.service;

import com.cinema.application.dto.BuyingTicketsDTO;
import com.cinema.application.dto.SeanceDTO;
import com.cinema.application.dto.TicketDTO;
import com.cinema.application.dto.UserDTO;
import com.cinema.application.dto.mapers.Mapper;
import com.cinema.domain.model.enums.Discount;
import com.cinema.domain.repository.TicketRepository;
import com.cinema.infrastructure.exceptions.AppException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Transactional
public class TicketService {
    private final TicketRepository ticketRepository;
    private final SeanceService seanceService;
    private final PlaceService placeService;
    private final MovieService movieService;
    private final UserService userService;

    public TicketService(TicketRepository ticketRepository, SeanceService seanceService, PlaceService placeService, MovieService movieService, UserService userService) {
        this.ticketRepository = ticketRepository;
        this.seanceService = seanceService;
        this.placeService = placeService;
        this.movieService = movieService;
        this.userService = userService;
    }


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

        if (buyingTicketsDTO == null) {
            throw new AppException("BUYING TICKET NULL");
        }
//        if(buyingTicketsDTO.getDiscounts().size()!=buyingTicketsDTO.getTicketQuantity()){
//////            throw new AppException("QUANTITY OF TICKETS ARE DIFFERENT THANE QUANTITY OD DISQOUNT");
//////        }

        UserDTO ticketBuyer = userService
                .findByEmail(buyingTicketsDTO.getEmail())
                .orElse(userService.add(UserDTO.builder()
                        .email(buyingTicketsDTO.getEmail())
                        .build())
                        .orElseThrow(() -> new AppException("ADD USER EXCEPTION")));

        SeanceDTO chosenSeance = seanceService
                .findByPlaceTitleDate(buyingTicketsDTO)
                .orElseThrow(() -> new AppException("SEANCE OUT OF BASE"));

        List<TicketDTO> tickets = new ArrayList<>();

        IntStream.range(0, buyingTicketsDTO.getTicketQuantity())
                .forEach(i -> tickets.add(0, TicketDTO.builder()
                        .userDTO(ticketBuyer)
                        .seanceDTO(chosenSeance)
                        .discount(()->
                        {
                            if (i >   buyingTicketsDTO.getDiscounts().toArray().length - 1) {
                                return Discount.NORMALNY;
                            } else {
                                return (Discount) buyingTicketsDTO.getDiscounts().toArray()[i];
                            }})
                        .price()
                                .build()));

                            return tickets;
                        }
    }
