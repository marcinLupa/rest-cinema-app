package com.cinema.application.service;

import com.cinema.application.dto.*;
import com.cinema.application.dto.enums.FilteringOption;
import com.cinema.application.dto.mapers.Mapper;
import com.cinema.domain.model.enums.Discount;
import com.cinema.domain.model.enums.Genre;
import com.cinema.domain.repository.TicketRepository;
import com.cinema.infrastructure.exceptions.AppException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
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

    /**
     * @role Role.ADMIN
     **/
    public Optional<TicketDTO> findOne(Long id) {
        if (id == null) {
            throw new AppException("FIND ONE TICKET EXCEPTION");
        }
        return Optional.of(Mapper.fromTicketToTicketDTO(ticketRepository
                .findOne(id)
                .orElseThrow()));
    }

    /**
     * @role Role.ADMIN
     **/
    public List<TicketDTO> findAll() {
        return ticketRepository
                .findAll()
                .stream()
                .map(Mapper::fromTicketToTicketDTO)
                .collect(Collectors.toList());
    }

    /**
     * @role Role.ADMIN
     **/
    public Optional<TicketDTO> add(TicketDTO ticketDTO) {
        if (ticketDTO == null) {
            throw new AppException("TICKET DTO ADD TICKET EXCEPTION");
        }
        return Optional.of(Mapper.fromTicketToTicketDTO(ticketRepository
                .save(Mapper.fromTicketDTOtoTicket(ticketDTO))
                .orElseThrow()));
    }

    /**
     * @role Role.ADMIN
     **/
    public void delete(Long id) {
        if (id == null) {
            throw new AppException("DELETE TICKET ID EXCEPTION");
        }
        ticketRepository.delete(id);
    }

    /**
     * Method buying tickets  ->
     * @role Role.USER
     * @param buyingTicketsDTO have fields cityName, movieName, startOfSeance,email, ticketQuantity, discounts
     * @return returns tickets that are committed to date base
     * - finding user by e-mail,
     * - finding seance by place, title and date
     * - Merging tickets by quantity, client gives us quantity of tickets and discount for those tickets that he
     * is entitled to a discount. Other tickets have default value "NORMALNY", price is count by basic price and
     * actual discount for ticket
     */

    public List<TicketDTO> buyingTickets(BuyingTicketsDTO buyingTicketsDTO) {

        if (buyingTicketsDTO == null) {
            throw new AppException("BUYING TICKET NULL");
        }

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
                            if (buyingTicketsDTO.getDiscounts().size() > 0 &&
                                    Arrays.stream(Discount.values())
                                            .noneMatch(discount -> buyingTicketsDTO.getDiscounts().contains(discount))) {
                                throw new AppException("VALUE IS NOT VALID ENUM TYPE");
                            }
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

    /***method getHistory  ->
     * @role Role.USER
     * @param historyDTO object contains necessary email address, filtering option and value
     * @return returns us List of TicketDTO by some filtering option
     * This method use switch case with enable preview to choose filtering option, and checking valid value of this options
     *
     */
    public List<TicketDTO> getHistory(HistoryDTO historyDTO) {
        if (historyDTO == null) {
            throw new AppException("HISTORY DTO IS NULL");
        }
        FilteringOption option = historyDTO.getOption();

        List<TicketDTO> filteredTickets = new ArrayList<>();

        switch (option) {
            case BY_MONTHS_TRANSACTION_DATE -> {
                if (!historyDTO.getValue().matches("[0-9]*")) {
                    throw new AppException("VALUE IS NOT INTEGER");
                }

                filteredTickets = ticketRepository
                        .findByTransactionDate(LocalDateTime
                                .now()
                                .minusMonths(Integer.parseInt(historyDTO.getValue())), historyDTO.getEmail())
                        .stream()
                        .map(Mapper::fromTicketToTicketDTO)
                        .collect(Collectors.toList());
            }
            case BY_PRICE -> {
                if (!historyDTO.getValue().matches("[0-9]*")) {
                    throw new AppException("VALUE IS NOT BIG DECIMAL");
                }
                filteredTickets = ticketRepository
                        .findAllByPriceBefore(new BigDecimal(historyDTO.getValue()), historyDTO.getEmail())
                        .stream()
                        .map(Mapper::fromTicketToTicketDTO)
                        .collect(Collectors.toList());
            }

            case BY_MOVIE_TITLE -> filteredTickets = ticketRepository
                    .findAllByMovieTitleAndUser(historyDTO.getValue(), historyDTO.getEmail())
                    .stream()
                    .map(Mapper::fromTicketToTicketDTO)
                    .collect(Collectors.toList());

            case BY_DISCOUNT -> {
                if (Arrays.stream(Discount.values())
                        .map(Enum::toString)
                        .noneMatch(discount -> discount.equals(historyDTO.getValue()))) {
                    throw new AppException("VALUE IS NOT VALID ENUM TYPE");
                }

                filteredTickets = ticketRepository
                        .findByDiscount(Discount.valueOf(historyDTO.getValue()), historyDTO.getEmail())
                        .stream()
                        .map(Mapper::fromTicketToTicketDTO)
                        .collect(Collectors.toList());
            }
            case BY_PLACE -> {
                if (!historyDTO.getValue().matches("[A-Z]*")) {
                    throw new AppException("VALUE IS NOT LETTER PATTERN");
                }
                filteredTickets =
                        ticketRepository.findAllByPlaceName(historyDTO.getValue(), historyDTO.getEmail())
                                .stream()
                                .map(Mapper::fromTicketToTicketDTO)
                                .collect(Collectors.toList());
            }
            case BY_MONTHS_SEANCE_DATE -> {
                if (!historyDTO.getValue().matches("[0-9]*")) {
                    throw new AppException("VALUE IS NOT INTEGER");
                }
                filteredTickets = ticketRepository
                        .findAllBySeanceStartOfSeance(LocalDateTime
                                .now()
                                .minusMonths(Integer.parseInt(historyDTO.getValue())), historyDTO.getEmail())
                        .stream()
                        .map(Mapper::fromTicketToTicketDTO)
                        .collect(Collectors.toList());
            }
            case BY_MOVIE_GENRE -> {
                if (Arrays.stream(Genre.values())
                        .map(Enum::toString)
                        .noneMatch(genre -> genre.equals(historyDTO.getValue()))) {
                    throw new AppException("VALUE IS NOT VALID ENUM TYPE");
                }
                filteredTickets = ticketRepository
                        .findByMovieGenre(Genre.valueOf(historyDTO.getValue()), historyDTO.getEmail())
                        .stream()
                        .map(Mapper::fromTicketToTicketDTO)
                        .collect(Collectors.toList());
            }
        }

        return filteredTickets;
    }
}



