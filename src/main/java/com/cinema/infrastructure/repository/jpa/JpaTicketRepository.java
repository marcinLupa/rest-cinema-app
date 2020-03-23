package com.cinema.infrastructure.repository.jpa;

import com.cinema.domain.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaTicketRepository extends JpaRepository<Ticket, Long> {
}
