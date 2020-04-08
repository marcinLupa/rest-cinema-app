package com.cinema.domain.model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@EqualsAndHashCode(exclude = {"tickets"})
@ToString(exclude = {"tickets"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "seances")
public class Seance {
    @Id
    @GeneratedValue
    private Long id;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm",iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime startOfSeance;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "movie")
    private Movie movie;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "place")
    private Place place;
    @OneToMany(mappedBy = "seance")
    private Set<Ticket> tickets;


}
