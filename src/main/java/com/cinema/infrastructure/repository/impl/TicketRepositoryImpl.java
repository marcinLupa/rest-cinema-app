package com.cinema.infrastructure.repository.impl;

import com.cinema.domain.model.Seance;
import com.cinema.domain.model.Ticket;
import com.cinema.domain.model.enums.Discount;
import com.cinema.domain.repository.TicketRepository;
import com.cinema.infrastructure.repository.jpa.JpaTicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TicketRepositoryImpl implements TicketRepository {
    private final JpaTicketRepository jpaTicketRepository;

    @Override
    public Optional<Ticket> findOne(Long id) {
        return jpaTicketRepository.findById(id);
    }

    @Override
    public List<Ticket> findAll() {
        return jpaTicketRepository.findAll();
    }

    @Override
    public Optional<Ticket> save(Ticket ticket) {
        return Optional.of(jpaTicketRepository.save(ticket));
    }

    @Override
    public void delete(Long id) {
        jpaTicketRepository.deleteById(id);
    }

    @Override
    public List<Ticket> findByMovieTitle(String title) {
        return jpaTicketRepository.findAllBySeance_Movie_Title(title);
    }

    @Override
    public List<Ticket> findByDiscount(Discount discount) {
        return jpaTicketRepository.findAllByDiscount(discount);
    }

    @Override
    public List<Ticket> findByUser(String email) {
        return jpaTicketRepository.findAllByUser_Email(email);
    }
}
