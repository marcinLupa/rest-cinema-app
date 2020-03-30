package com.cinema.domain.model;

import com.cinema.domain.model.enums.Genre;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"seances"})
@ToString(exclude = {"seances"})
@Setter
@Getter

@Entity
@Table(name = "movies")
public class Movie {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private Integer duration;
    @Enumerated(EnumType.STRING)
    private Genre genre;
    @OneToMany(mappedBy = "movie")
    private Set<Seance> seances;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private User user;

}
