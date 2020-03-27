package com.cinema.application.dto;

import com.cinema.application.dto.enums.FilteringOption;
import com.cinema.domain.model.enums.Genre;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilteringMoviesDTO {

    private FilteringOption option;
    private MovieDTO movieDTO;


}
