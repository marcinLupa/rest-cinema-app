package com.cinema.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlaceDTO {
    private Long id;
    private String name;

    @Override
    public String toString() {
        return "PlaceDTO id=" + id + "\n" +
                "   name='" + name + "\n";
    }
}
