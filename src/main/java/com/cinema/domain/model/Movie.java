package com.cinema.domain.model;

import com.cinema.domain.model.enums.Genre;
import com.cinema.domain.model.generic.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Set;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter

@Entity
@Table(name = "movies")
public class Movie extends BaseEntity {

    private String title;
    private Integer duration;

    @Enumerated(EnumType.STRING)
    private Genre genre;

    @OneToMany(mappedBy = "movie")
    private Set<Seance> seances;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private User user;

}
