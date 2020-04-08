package com.cinema.application.dto;

import com.cinema.application.dto.enums.FilteringOption;
import com.cinema.domain.model.Ticket;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
public class HistoryDTO {

    private String email;
    private FilteringOption option;
    private String value;

}
