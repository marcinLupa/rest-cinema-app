package com.cinema.domain.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Setter
@Getter

@Entity
@Table(name = "seances")
public class Seance {
    @Id
    @GeneratedValue
    private Long id;
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
