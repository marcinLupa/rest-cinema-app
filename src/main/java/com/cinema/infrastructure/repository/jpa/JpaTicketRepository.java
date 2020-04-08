package com.cinema.infrastructure.repository.jpa;

import com.cinema.domain.model.Ticket;
import com.cinema.domain.model.enums.Discount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaTicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> findAllBySeance_Movie_Title(String title);

    List<Ticket> findAllByDiscount(Discount discount);

    List<Ticket> findAllByUser_Email(String email);
}
