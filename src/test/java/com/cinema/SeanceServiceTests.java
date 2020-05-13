package com.cinema;

import com.cinema.application.dto.FilteringMoviesDTO;
import com.cinema.application.service.MovieService;
import com.cinema.application.validator.impl.FilteringMovieDtoValidator;
import com.cinema.domain.model.Movie;
import com.cinema.domain.model.Place;
import com.cinema.domain.model.Seance;
import com.cinema.domain.model.enums.Genre;
import com.cinema.infrastructure.exceptions.AppException;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.cinema.application.dto.enums.FilteringOption.GENRE;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class SeanceServiceTests {
    private final List<Seance> seances = List.of(
            Seance
                    .builder()
                    .startOfSeance(LocalDateTime.parse("2017-11-09T10:44", DateTimeFormatter.ISO_DATE_TIME))
                    .movie(Movie.builder()
                            .duration(120)
                            .genre(Genre.COMEDY)
                            .title("ACE VENTURA")
                            .build())
                    .place(Place.builder()
                            .name("LEGNICA")
                            .build())
                    .build()
    );
    //    @Mock
//    private SeanceRepository seanceRepository;
//
//    @InjectMocks
//    private SeanceService seanceService;
//
    @Mock
    private FilteringMovieDtoValidator validator;

    @InjectMocks
    private MovieService movieService;
/**good**/
    @Test
    @DisplayName("Movie Get Movies - test 1")
    public void test1() {
        Map<String,String> exc=new HashMap<>();
        exc.put("egz1","egz1");

        FilteringMoviesDTO filteringMoviesDTO = FilteringMoviesDTO.builder()
                .option(GENRE)
                .value("AAA")
                .build();
        Mockito.when(validator.validate(filteringMoviesDTO))
                .thenReturn(exc);

        Mockito.when(validator.hasErrors()).thenReturn(true);

        Mockito.when(validator.getExceptionMessage())
                .thenReturn("egz1","egz1");

        AppException app = Assertions.assertThrows(
                AppException.class,
                () -> movieService.getMovies(filteringMoviesDTO));
        System.out.println(app.getMessage());

        Assert.assertNotNull(app);

    }
}


