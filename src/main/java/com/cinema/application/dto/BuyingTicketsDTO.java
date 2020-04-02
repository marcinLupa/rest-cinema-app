package com.cinema.application.dto;

import com.cinema.domain.model.enums.Discount;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.EnumSet;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuyingTicketsDTO {

    private String cityName;
    private String movieName;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime startOfSeance;
    private String email;
    private Integer ticketQuantity;
    private EnumSet<Discount> discounts;

}
