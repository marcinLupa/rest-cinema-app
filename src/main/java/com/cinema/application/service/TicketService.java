package com.cinema.application.service;

import com.cinema.application.dto.*;
import com.cinema.application.dto.enums.FilteringOption;
import com.cinema.application.dto.mapers.Mapper;
import com.cinema.domain.model.Ticket;
import com.cinema.domain.model.enums.Discount;
import com.cinema.domain.repository.TicketRepository;
import com.cinema.infrastructure.exceptions.AppException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@Service
@Transactional
public class TicketService {
    private final TicketRepository ticketRepository;
    private final SeanceService seanceService;
    private final UserService userService;

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
                        .transactionDate()
                        .discount(() ->
                        {
                            if (i > buyingTicketsDTO.getDiscounts().toArray().length - 1) {
                                return Discount.NORMALNY;
                            } else {
                                return (Discount) buyingTicketsDTO.getDiscounts().toArray()[i];
                            }
                        })
                        .price()
                        .build()));

        return tickets;
    }

    public List<TicketDTO> getHistory(HistoryDTO historyDTO) {
        if (historyDTO == null) {
            throw new AppException("HISTORY DTO IS NULL");
        }

        FilteringOption option = historyDTO.getOption();

        List<TicketDTO> filteredTickets = ticketRepository
                .findByUser(historyDTO.getEmail())
                .stream()
                .map(Mapper::fromTicketToTicketDTO)
                .collect(Collectors.toList());

        ticketRepository.findByUser(historyDTO.getEmail())
                .forEach(System.out::println);
        switch (option) {
            case BY_MONTHS -> filteredTickets = filteredTickets
                    .stream()
                    .filter(x -> x.getTransactionDate()
                            .isAfter(LocalDateTime
                                    .now()
                                    .minusMonths(Optional
                                            .of(Integer.parseInt(historyDTO.getValue()))
                                            .orElseThrow(() -> new AppException("VALUE IS NOT INTEGER")))))
                    .collect(Collectors.toList());

            case BY_PRICE -> filteredTickets = filteredTickets
                    .stream()
                    .filter(x -> x.getPrice()
                            .compareTo(Optional
                                    .of(new BigDecimal(historyDTO.getValue()))
                                    .orElseThrow(() -> new AppException("VALUE IS NOT BIG DECIMAL"))) < 1)
                    .collect(Collectors.toList());

            case BY_MOVIE -> filteredTickets = filteredTickets
                    .stream()
                    .filter(x -> x.getSeanceDTO().getMovieDTO().getTitle().equals(historyDTO.getValue()))
                    .collect(Collectors.toList());

            case BY_DISCOUNT -> filteredTickets = filteredTickets
                    .stream()
                    .filter(x -> x.getDiscount()
                            .equals(Optional
                                    .of(Discount.valueOf(historyDTO.getValue()))
                                    .orElseThrow(() -> new AppException("VALUE IS NOT NOT VALID ENUM TYPE"))))
                    .collect(Collectors.toList());
        }
        return filteredTickets;
    }
}



