package com.cinema.domain.model;

import com.cinema.domain.model.enums.KindOfTicket;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Setter
@Getter

@Entity
@Table(name = "tickets")
public class Ticket {
    @Id
    @GeneratedValue
    private Long id;
    private BigDecimal price;
    private Integer discount;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "seance")
    private Seance seance;
    private Integer quantity;
    @Enumerated(EnumType.STRING)
    private KindOfTicket kindOfTicket;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="user")
    private User user;

    public void setKindOfTicket() {
        if(user.getAge()<25 & user.getAge()>18){
            this.kindOfTicket=KindOfTicket.STUDENCKI;
        }
        if(user.getAge()<18){
            this.kindOfTicket=KindOfTicket.DZIECKO;
        }
        if(user.getAge()>60){
            this.kindOfTicket=KindOfTicket.SENIOR;
        }
        if(user.getAge()>18&user.getAge()<60){
            this.kindOfTicket=KindOfTicket.NORMALNY;
        }
    }
}
