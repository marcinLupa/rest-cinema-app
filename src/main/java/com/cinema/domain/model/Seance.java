package com.cinema.domain.model;

import com.cinema.domain.model.generic.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter

@Entity
@Table(name = "seances")
public class Seance extends BaseEntity {


        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm", iso = DateTimeFormat.ISO.DATE_TIME)
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
