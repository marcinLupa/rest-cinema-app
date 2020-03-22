package com.cinema.domain.model;

import lombok.*;

import javax.persistence.*;

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

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "movie")
    private Movie movie;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "place")
    private Place place;


}
