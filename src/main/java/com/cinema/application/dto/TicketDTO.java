package com.cinema.application.dto;

import com.cinema.domain.model.enums.Discount;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import java.time.LocalDateTime;

import java.util.Objects;
import java.util.function.Supplier;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class TicketDTO {

    private Long id;
    private BigDecimal price;
    private Discount discount;
    private SeanceDTO seanceDTO;
    private UserDTO userDTO;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime transactionDate;

    public static TicketDTOBuilder builder() {
        return new TicketDTOBuilder();
    }

    public String toJson() {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        return gson.toJson(this);
    }

    //q
    public String toString() {
        return "ticketDTO" + toJson();
    }

    @Component
    public static class TicketDTOBuilder {
        public static final BigDecimal STANDARD_PRICE = new BigDecimal(10);

        private Long id;
        private BigDecimal price;
        private Discount discount;
        private SeanceDTO seanceDTO;
        private UserDTO userDTO;
        private LocalDateTime transactionDate;

        TicketDTOBuilder() {
        }

        public TicketDTOBuilder id(Long id) {
            this.id = id;
            return this;
        }


        public TicketDTOBuilder price() {
            if (discount.discount.compareTo(new BigDecimal(0)) == 0) {
                this.price = STANDARD_PRICE;
                return this;
            }
            this.price = STANDARD_PRICE.subtract(
                    STANDARD_PRICE.multiply(discount.discount)
                            .divide(new BigDecimal(100)));
            return this;
        }

        public TicketDTOBuilder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public TicketDTOBuilder discount(Discount discount) {
            this.discount = Objects.requireNonNullElse(discount, Discount.NORMALNY);
            return this;
        }

        public TicketDTOBuilder discount(Supplier<Discount> supplier) {
            this.discount = supplier.get();
            return this;
        }

        public TicketDTOBuilder seanceDTO(SeanceDTO seanceDTO) {
            this.seanceDTO = seanceDTO;
            return this;
        }

        public TicketDTOBuilder userDTO(UserDTO userDTO) {
            this.userDTO = userDTO;
            return this;
        }

        public TicketDTOBuilder transactionDate() {
            this.transactionDate = LocalDateTime.now();
            return this;
        }

        public TicketDTOBuilder transactionDate(LocalDateTime transactionDate) {
            this.transactionDate = transactionDate;
            return this;
        }

        public TicketDTO build() {
            return new TicketDTO(id, price, discount, seanceDTO, userDTO, transactionDate);
        }

        public String toString() {
            return "TicketDTO.TicketDTOBuilder(id=" + this.id +
                    ", price=" + this.price +
                    ", discount=" + this.discount +
                    ", seanceDTO=" + this.seanceDTO +
                    ", userDTO=" + this.userDTO + ")";
        }
    }

}
