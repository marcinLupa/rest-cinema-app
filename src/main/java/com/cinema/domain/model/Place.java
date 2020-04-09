package com.cinema.domain.model;

import com.cinema.domain.model.generic.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Setter
@Getter


@Entity
@Table(name = "places")
public class Place extends BaseEntity {

    private String name;

    @OneToMany(mappedBy = "place")
    private Set<Seance> seances;

}
