package com.cinema.infrastructure.repository.impl;

import com.cinema.domain.model.Ticket;
import com.cinema.domain.model.enums.Discount;
import com.cinema.domain.model.enums.Genre;
import com.cinema.domain.repository.TicketRepository;
import com.cinema.infrastructure.repository.jpa.JpaTicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
    public List<Ticket> findByMovieGenre(Genre genre, String email) {
        return jpaTicketRepository.findAllBySeance_Movie_GenreAndUser_Email(genre, email);
    }

    @Override
    public List<Ticket> findByDiscount(Discount discount, String email) {
        return jpaTicketRepository.findAllByDiscountAndUser_Email(discount, email);
    }

    @Override
    public List<Ticket> findByTransactionDate(LocalDateTime transactionDate, String email) {
        return jpaTicketRepository.findAllByTransactionDateAfterAndUser_Email(transactionDate, email);
    }

    @Override
    public List<Ticket> findAllByPriceBefore(BigDecimal price, String email) {
        return jpaTicketRepository.findAllByPriceBeforeAndUser_Email(price, email);
    }

    @Override
    public List<Ticket> findAllByPlaceName(String place, String email) {
        return jpaTicketRepository.findAllBySeance_Place_NameAndUser_Email(place, email);
    }

    @Override
    public List<Ticket> findAllBySeanceStartOfSeance(LocalDateTime startOfSeance, String email) {
        return jpaTicketRepository.findAllBySeanceStartOfSeanceIsAfterAndUser_Email(startOfSeance, email);
    }

    @Override
    public List<Ticket> findAllByMovieTitleAndUser(String title, String email) {
        return jpaTicketRepository.findAllBySeance_Movie_TitleAndUser_Email(title, email);
    }
}
