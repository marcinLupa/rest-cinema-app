package com.cinema.domain.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;

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

}
