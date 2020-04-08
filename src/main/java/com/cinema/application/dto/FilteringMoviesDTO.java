package com.cinema.application.dto;

import com.cinema.application.dto.enums.FilteringOption;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilteringMoviesDTO {

    private FilteringOption option;
    private String value;


}
