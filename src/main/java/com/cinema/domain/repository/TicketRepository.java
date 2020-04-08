package com.cinema.domain.repository;

import com.cinema.domain.model.Ticket;
import com.cinema.domain.model.enums.Discount;
import com.cinema.domain.model.enums.Genre;
import com.cinema.domain.repository.generic.GenericRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface TicketRepository extends GenericRepository<Ticket, Long> {

    List<Ticket> findByMovieGenre(Genre genre, String email);

    List<Ticket> findByDiscount(Discount discount, String email);


    List<Ticket> findByTransactionDate(LocalDateTime transactionDate, String email);

    List<Ticket> findAllByPriceBefore(BigDecimal price, String email);

    List<Ticket> findAllByPlaceName(String place, String email);

    List<Ticket> findAllBySeanceStartOfSeance(LocalDateTime startOfSeance, String email);

    List<Ticket> findAllByMovieTitleAndUser(String title, String email);


}
