package com.cinema.infrastructure.repository.jpa;

import com.cinema.domain.model.Ticket;
import com.cinema.domain.model.enums.Discount;
import com.cinema.domain.model.enums.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface JpaTicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> findAllByDiscountAndUser_Email(Discount discount,String email);

   // List<Ticket> findAllByUser_Email(String email);

    List<Ticket> findAllByTransactionDateAfterAndUser_Email(LocalDateTime transactionDate,String email);

    List<Ticket> findAllByPriceBeforeAndUser_Email(BigDecimal price, String email);

    List<Ticket> findAllBySeance_Place_NameAndUser_Email(String place,String email);

    List<Ticket> findAllBySeanceStartOfSeanceIsAfterAndUser_Email(LocalDateTime startOfSeance,String email);

    List<Ticket> findAllBySeance_Movie_GenreAndUser_Email(Genre genre,String email);

    List<Ticket> findAllBySeance_Movie_TitleAndUser_Email(String title, String email);

}
