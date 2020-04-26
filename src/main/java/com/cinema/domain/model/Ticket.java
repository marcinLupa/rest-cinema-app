package com.cinema.domain.model;

import com.cinema.domain.model.enums.Discount;
import com.cinema.domain.model.generic.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter

@Entity
@Table(name = "tickets")
public class Ticket extends BaseEntity {

    private BigDecimal price;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "seance")
    private Seance seance;

    @Enumerated(EnumType.STRING)
    private Discount discount;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user")
    private User user;

    private LocalDateTime transactionDate;



}
