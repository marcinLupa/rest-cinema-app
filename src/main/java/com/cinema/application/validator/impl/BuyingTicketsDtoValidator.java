package com.cinema.application.validator.impl;

import com.cinema.application.dto.BuyingTicketsDTO;
import com.cinema.application.exceptions.TicketServiceException;
import com.cinema.application.validator.AbstractValidator;
import com.cinema.infrastructure.exceptions.AppException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

@Component
public class BuyingTicketsDtoValidator extends AbstractValidator<BuyingTicketsDTO> {
    @Override
    public Map<String, String> validate(BuyingTicketsDTO item) {
        errors.clear();

        if (item == null) {
            errors.put("BUYING TICKET VALIDATOR", "BUYING TICKET DTO IS NULL");
        }

        if (!(item != null && item.getEmail().matches("^(.+)@(.+)$"))) {
            errors.put("BUYING TICKET VALIDATOR", "GIVEN STRING IS NOT EMAIL ADDRESS");
        }
        if (item != null && item.getCityName().matches("[A-Za-z]*")) {
            errors.put("BUYING TICKET VALIDATOR", "STRING IS NOT CITY NAME");
        }
        if (item != null && item.getTicketQuantity() < 1) {
            errors.put("BUYING TICKET VALIDATOR", "TICKETS MINIMUM QUANTITY 1");
        }
        if (item != null && item.getTicketQuantity() > 20) {
            errors.put("BUYING TICKET VALIDATOR", "TICKETS MAXIMUM QUANTITY 20");
        }
        if (item != null && item.getStartOfSeance().isBefore(LocalDateTime.now())) {
            errors.put("BUYING TICKET VALIDATOR", "START OF SEANCE IS FROM THE PAST");
        }
        if (item != null && item.getStartOfSeance().isAfter(LocalDateTime.now().plusMonths(6))) {
            errors.put("BUYING TICKET VALIDATOR", "START OF SEANCE IS TOO FAR, ONLY 6 MONTHS FROM NOW YOU CAN BY TICKET");
        }
        if (item != null && item.getMovieName().matches("[A-Za-z0-9]*")) {
            errors.put("BUYING TICKET VALIDATOR", "STRING IS NOT MOVIE NAME");
        }
        if (item != null && item.getDiscounts().size() > item.getTicketQuantity()) {
            errors.put("BUYING TICKET VALIDATOR", "QUANTITY OF DISCOUNTS BIGGER THEN TICKETS QUANTITY");
        }
        return errors;
    }
}
