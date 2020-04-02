package com.cinema.domain.model;

import com.cinema.domain.model.enums.Discount;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
@Getter
@Setter
@Entity
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue
    private Long id;


    private BigDecimal price;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "seance")
    private Seance seance;

    @Enumerated(EnumType.STRING)
    private Discount discount;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user")
    private User user;

//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public void setPrice(BigDecimal price) {
//        this.price = price
//                .add(getDiscount().discount.multiply(price)
//                        .divide(new BigDecimal(100), RoundingMode.CEILING));
//    }
//
//    public void setSeance(Seance seance) {
//        this.seance = seance;
//    }
//
//    public void setDiscount(Discount discount) {
//        if (discount == null) {
//            this.discount = Discount.NORMALNY;
//        } else {
//            this.discount = discount;
//        }
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }


//    public Ticket(TicketBuilder ticketBuilder) {
//        this.id = ticketBuilder.id;
//        this.price = ticketBuilder.price;
//        this.seance = ticketBuilder.seance;
//        this.discount = ticketBuilder.discount;
//        this.user = ticketBuilder.user;
//
//
//    }

//    public static final class TicketBuilder {
//        private Long id;
//        private BigDecimal price;
//        private Seance seance;
//        private Discount discount;
//        private User user;
//
//        private TicketBuilder() {
//        }
//
//        public static TicketBuilder aTicket() {
//
//            return new TicketBuilder();
//        }
//
//        public TicketBuilder withId(Long id) {
//            this.id = id;
//            return this;
//        }
//
//        public TicketBuilder withPrice(BigDecimal price) {
//            this.price = price;
//            return this;
//        }
//
//        public TicketBuilder withSeance(Seance seance) {
//            this.seance = seance;
//            return this;
//        }
//
//        public TicketBuilder withDiscount(Discount discount) {
//            this.discount = discount;
//            return this;
//        }
//
//        public TicketBuilder withUser(User user) {
//            this.user = user;
//            return this;
//}
//
//    public Ticket build() {
//        Ticket ticket = new Ticket();
//        ticket.setId(id);
//        ticket.setPrice(price);
//        ticket.setSeance(seance);
//        ticket.setDiscount(discount);
//        ticket.setUser(user);
//        return ticket;
//    }
//}

}
