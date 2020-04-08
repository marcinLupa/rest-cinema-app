package com.cinema.application.dto;

import com.cinema.domain.model.enums.Genre;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieDTO {

    private String title;
    private Integer duration;
    private Genre genre;

    @Override
    public String toString() {
        return  """
                MovieDTO"""+
                "\n"+"   title='" + title + "\n"+
                "   duration=" + duration  +"\n"+
                "   genre=" + genre  +"\n";
    }
}
