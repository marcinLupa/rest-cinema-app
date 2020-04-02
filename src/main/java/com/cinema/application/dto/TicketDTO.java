package com.cinema.application.dto;

import com.cinema.domain.model.enums.Discount;
import lombok.Getter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

@Getter
@ToString
public class TicketDTO {

    public final BigDecimal STANDARD_PRICE = new BigDecimal(15);

    private Long id;
    private BigDecimal price;
    @Value("${ticket.price}")
    private Discount discount;
    private SeanceDTO seanceDTO;
    private UserDTO userDTO;

    public TicketDTO(Long id, BigDecimal price, Discount discount, SeanceDTO seanceDTO, UserDTO userDTO) {
        this.id = id;
        this.price = price;
        this.discount = discount;
        this.seanceDTO = seanceDTO;
        this.userDTO = userDTO;
    }

    public TicketDTO() {
    }

    public static TicketDTOBuilder builder() {
        return new TicketDTOBuilder();
    }

    public void setPrice(BigDecimal price) {
        this.price = STANDARD_PRICE
                .divide(getDiscount().discount.multiply(STANDARD_PRICE)
                        .divide(new BigDecimal(100), RoundingMode.CEILING), RoundingMode.CEILING);
    }

    public void setDiscount(Discount discount) {
        this.discount = Objects.requireNonNullElse(discount, Discount.NORMALNY);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setSeanceDTO(SeanceDTO seanceDTO) {
        this.seanceDTO = seanceDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public static class TicketDTOBuilder {
        public final BigDecimal STANDARD_PRICE = new BigDecimal(15);


        private Long id;
        @Value("${ticket.price}")
        private BigDecimal price;
        private Discount discount;
        private SeanceDTO seanceDTO;
        private UserDTO userDTO;

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
            this.price = STANDARD_PRICE
                    .divide((STANDARD_PRICE).multiply(discount.discount)
                            .divide(new BigDecimal(100), RoundingMode.CEILING), RoundingMode.CEILING);

            return this;
    }

    public TicketDTOBuilder discount(Discount discount) {
        this.discount = Objects.requireNonNullElse(discount, Discount.NORMALNY);
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

    public TicketDTO build() {
        return new TicketDTO(id, price, discount, seanceDTO, userDTO);
    }

    public String toString() {
        return "TicketDTO.TicketDTOBuilder(id=" + this.id + ", price=" + this.price + ", discount=" + this.discount + ", seanceDTO=" + this.seanceDTO + ", userDTO=" + this.userDTO + ")";
    }
}

}
