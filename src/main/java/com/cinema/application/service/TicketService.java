package com.cinema.application.service;

import com.cinema.application.dto.*;
import com.cinema.application.dto.enums.FilteringOption;
import com.cinema.application.exceptions.TicketServiceException;
import com.cinema.application.validator.impl.BuyingTicketsDtoValidator;
import com.cinema.application.validator.impl.HistoryDtoValidator;
import com.cinema.domain.model.Seance;
import com.cinema.domain.model.Ticket;
import com.cinema.domain.model.User;
import com.cinema.domain.model.enums.Discount;
import com.cinema.domain.model.enums.Genre;
import com.cinema.domain.repository.TicketRepository;
import com.cinema.infrastructure.exceptions.AppException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@Service
@Transactional
public class TicketService {
    private final TicketRepository ticketRepository;
    private final SeanceService seanceService;
    private final UserService userService;
    private final BuyingTicketsDtoValidator buyingValidator;
    private final HistoryDtoValidator historyDtoValidator;

    /**
     * @role Role.ADMIN
     **/
    public Optional<Ticket> findOne(Long id) {
        if (id == null) {
            throw new AppException("FIND ONE TICKET EXCEPTION");
        }
        return Optional.of(ticketRepository
                .findOne(id)
                .orElseThrow());
    }

    /**
     * @role Role.ADMIN
     **/
    public List<Ticket> findAll() {
        return ticketRepository
                .findAll();
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
     *
     * @param buyingTicketsDTO have fields cityName, movieName, startOfSeance,email, ticketQuantity, discounts
     * @return returns tickets that are committed to date base
     * - finding user by e-mail,
     * - finding seance by place, title and date
     * - Merging tickets by quantity, client gives us quantity of tickets and discount for those tickets that he
     * is entitled to a discount. Other tickets have default value "NORMALNY", price is count by basic price and
     * actual discount for ticket
     * @role Role.USER
     */

    public List<Ticket> buyingTickets(BuyingTicketsDTO buyingTicketsDTO) {

        buyingValidator.validate(buyingTicketsDTO);

        if (buyingValidator.hasErrors()) {
            throw new TicketServiceException(buyingValidator.getExceptionMessage());
        }

        User ticketBuyer = userService
                .findByEmail(buyingTicketsDTO.getEmail())
                .orElseThrow(() -> new TicketServiceException("USER OUT OF BASE"));

        Seance chosenSeance = seanceService
                .findByPlaceTitleDate(buyingTicketsDTO)
                .orElseThrow(() -> new AppException("SEANCE OUT OF BASE"));

        List<Ticket> tickets = new ArrayList<>();

        IntStream.range(0, buyingTicketsDTO.getTicketQuantity())
                .forEach(ticketNumber -> tickets.add(0, Ticket.builder()
                        .user(ticketBuyer)
                        .seance(chosenSeance)
                        .transactionDate(LocalDateTime.now())
                        .discount(countedDiscount(buyingTicketsDTO.getDiscounts(), ticketNumber))
                        .price(countedPrice((Discount) buyingTicketsDTO.getDiscounts().toArray()[ticketNumber]))
                        .build()));

        return tickets;
    }

    private BigDecimal countedPrice(Discount discount) {
        final BigDecimal STANDARD_PRICE = new BigDecimal(20);

        BigDecimal price;
        if (discount.discount.compareTo(new BigDecimal(0)) == 0) {
            price = new BigDecimal(20);
        } else {
            price = STANDARD_PRICE.subtract(
                    STANDARD_PRICE.multiply(discount.discount)
                            .divide(new BigDecimal(100)));
        }
        return price;
    }

    private Discount countedDiscount(EnumSet<Discount> discounts, int quantity) {
        if (discounts.size() > 0 &&
                Arrays.stream(Discount.values())
                        .noneMatch(discounts::contains)) {
            throw new AppException("VALUE IS NOT VALID ENUM TYPE");
        }
        if (quantity > discounts.toArray().length - 1) {
            return Discount.NORMALNY;
        } else {
            return (Discount) discounts.toArray()[quantity];
        }
    }

    /***method getHistory  ->
     * @role Role.USER
     * @param historyDTO object contains necessary email address, filtering option and value
     * @return returns us List of TicketDTO by some filtering option
     * This method use switch case with enable preview to choose filtering option, and checking valid value of this options
     *
     */
    public List<Ticket> getHistory(HistoryDTO historyDTO) {

        historyDtoValidator.validate(historyDTO);
        if (historyDtoValidator.hasErrors()) {
            throw new TicketServiceException(historyDtoValidator.getExceptionMessage());
        }
        FilteringOption option = historyDTO.getOption();

        List<Ticket> filteredTickets = new ArrayList<>();

        switch (option) {
            case BY_MONTHS_TRANSACTION_DATE -> {

                filteredTickets = ticketRepository
                        .findByTransactionDate(LocalDateTime
                                .now()
                                .minusMonths(Integer.parseInt(historyDTO.getValue())), historyDTO.getEmail());
            }
            case BY_PRICE -> {

                filteredTickets =ticketRepository
                        .findAllByPriceBefore(new BigDecimal(historyDTO.getValue()), historyDTO.getEmail());
            }

            case BY_MOVIE_TITLE -> filteredTickets = ticketRepository
                    .findAllByMovieTitleAndUser(historyDTO.getValue(), historyDTO.getEmail());

            case BY_DISCOUNT -> {

                filteredTickets = ticketRepository
                        .findByDiscount(Discount.valueOf(historyDTO.getValue()), historyDTO.getEmail());
            }
            case BY_PLACE -> {

                filteredTickets =
                   ticketRepository.findAllByPlaceName(historyDTO.getValue(), historyDTO.getEmail());
            }
            case BY_MONTHS_SEANCE_DATE -> {

                filteredTickets = (ticketRepository
                        .findAllBySeanceStartOfSeance(LocalDateTime
                                .now()
                                .minusMonths(Integer.parseInt(historyDTO.getValue())), historyDTO.getEmail()));
            }
            case BY_MOVIE_GENRE -> {

                filteredTickets = ticketRepository
                        .findByMovieGenre(Genre.valueOf(historyDTO.getValue()), historyDTO.getEmail());
            }
        }

        return filteredTickets;
    }
}



