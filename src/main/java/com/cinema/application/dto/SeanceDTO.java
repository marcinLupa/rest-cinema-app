package com.cinema.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeanceDTO {
    private Long id;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime startOfSeance;
    private MovieDTO movieDTO;
    private PlaceDTO placeDTO;

    @Override
    public String toString() {
        return "SeanceDTO id=" + id  +"\n"+
                "   startOfSeance=" + startOfSeance  +"\n"+
               movieDTO  +"\n"+
                placeDTO;
    }
}
