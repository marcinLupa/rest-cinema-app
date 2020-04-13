package com.cinema.application.validator.impl;

import com.cinema.application.dto.HistoryDTO;
import com.cinema.application.dto.enums.FilteringOption;
import com.cinema.application.validator.AbstractValidator;
import com.cinema.domain.model.enums.Discount;
import com.cinema.domain.model.enums.Genre;
import com.cinema.infrastructure.exceptions.AppException;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

public class HistoryDtoValidator extends AbstractValidator<HistoryDTO> {
    @Override
    public Map<String, String> validate(HistoryDTO item) {
        errors.clear();
        if (item == null) {
            errors.put("HISTORY VALIDATOR EXCEPTION", "HISTORY DTO IS NULL");
        }
        if (Objects.requireNonNull(item).getOption().equals(FilteringOption.BY_MONTHS_TRANSACTION_DATE)
                && !item.getValue().matches("[0-9]*")) {
            errors.put("HISTORY VALIDATOR EXCEPTION", "VALUE IS NOT INTEGER");
        }
        if (Objects.requireNonNull(item).getOption().equals(FilteringOption.BY_MONTHS_TRANSACTION_DATE)
                && Integer.parseInt(item.getValue()) < 1) {
            errors.put("HISTORY VALIDATOR EXCEPTION", "QUANTITY OF MONTH'S MUST BE BIGGER THEN 0");
        }
        if (Objects.requireNonNull(item).getOption().equals(FilteringOption.BY_PRICE)
                && !item.getValue().matches("[0-9]*")) {
            errors.put("HISTORY VALIDATOR EXCEPTION", "VALUE IS BIG DECIMAL");
        }
        if (Objects.requireNonNull(item).getOption().equals(FilteringOption.BY_PRICE)
                && new BigDecimal(item.getValue()).compareTo(BigDecimal.ZERO) < 0) {
            errors.put("HISTORY VALIDATOR EXCEPTION", "PRICE UNDER 0");
        }
        if (Objects.requireNonNull(item).getOption().equals(FilteringOption.BY_MOVIE_TITLE)
                && !item.getValue().matches("[A-Za-z0-9]*")) {
            errors.put("HISTORY VALIDATOR EXCEPTION", "STRING IS NOT MOVIE TITLE");
        }
        if (Objects.requireNonNull(item).getOption().equals(FilteringOption.BY_DISCOUNT)
                && Arrays.stream(Discount.values())
                .map(Enum::toString)
                .noneMatch(discount -> discount.equals(item.getValue()))) {
            errors.put("HISTORY VALIDATOR EXCEPTION", ("VALUE IS NOT VALID ENUM TYPE"));
        }
        if (Objects.requireNonNull(item).getOption().equals(FilteringOption.BY_PLACE)
                && !item.getValue().matches("[A-Za-z0-9]*")) {
            errors.put("HISTORY VALIDATOR EXCEPTION", "STRING IS NOT PLACE NAME");
        }
        if (Objects.requireNonNull(item).getOption().equals(FilteringOption.BY_MONTHS_SEANCE_DATE)
                && Integer.parseInt(item.getValue()) < 1) {
            errors.put("HISTORY VALIDATOR EXCEPTION", "QUANTITY OF MONTH'S MUST BE BIGGER THEN 0");
        }
        if (Objects.requireNonNull(item).getOption().equals(FilteringOption.BY_MONTHS_SEANCE_DATE)
                && !item.getValue().matches("[0-9]*")) {
            errors.put("HISTORY VALIDATOR EXCEPTION", "VALUE IS NOT INTEGER");
        }
        if (Objects.requireNonNull(item).getOption().equals(FilteringOption.BY_MOVIE_GENRE)
                && Arrays.stream(Genre.values())
                .map(Enum::toString)
                .noneMatch(genre -> genre.equals(item.getValue()))) {
            errors.put("HISTORY VALIDATOR EXCEPTION", "VALUE IS NOT VALID ENUM TYPE");

        }
        return errors;
    }
}
