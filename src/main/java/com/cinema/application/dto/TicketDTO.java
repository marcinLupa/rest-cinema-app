package com.cinema.application.dto;

import com.cinema.domain.model.Seance;
import com.cinema.domain.model.User;
import com.cinema.domain.model.enums.KindOfTicket;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketDTO {

    private Long id;
    private BigDecimal price;
    private Integer discount;
    private SeanceDTO seanceDTO;
    private Integer quantity;
    private KindOfTicket kindOfTicket;
    private UserDTO userDTO;
}
