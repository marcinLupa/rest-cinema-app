package com.cinema.domain.model;

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
@Table(name = "places")
public class Place {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @OneToMany(mappedBy = "place")
    private Set<Seance> seances;

}
