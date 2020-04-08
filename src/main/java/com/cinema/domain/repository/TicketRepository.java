package com.cinema.domain.repository;

import com.cinema.application.dto.TicketDTO;
import com.cinema.domain.model.Ticket;
import com.cinema.domain.model.enums.Discount;
import com.cinema.domain.repository.generic.GenericRepository;

import java.util.List;

public interface TicketRepository extends GenericRepository<Ticket, Long> {
    List<Ticket> findByMovieTitle(String title);

    List<Ticket> findByDiscount(Discount discount);

    List<Ticket> findByUser(String email);
}
