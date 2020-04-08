package com.cinema.application.dto;

import com.cinema.application.dto.enums.FilteringOption;
import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
public class HistoryDTO {

    private String email;
    private FilteringOption option;
    private String value;

}
