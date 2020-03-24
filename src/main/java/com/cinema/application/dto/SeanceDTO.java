package com.cinema.application.dto;

import com.cinema.domain.model.Movie;
import com.cinema.domain.model.Place;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeanceDTO {
    private Long id;
    private LocalDateTime startOfSeance;
    private MovieDTO movieDTO;
    private PlaceDTO placeDTO;
}
