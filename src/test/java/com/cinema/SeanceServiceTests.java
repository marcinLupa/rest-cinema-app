package com.cinema;

import com.cinema.application.dto.BuyingTicketsDTO;
import com.cinema.application.dto.SeanceDTO;
import com.cinema.application.service.SeanceService;
import com.cinema.domain.model.Movie;
import com.cinema.domain.model.Place;
import com.cinema.domain.model.Seance;
import com.cinema.domain.model.enums.Genre;
import com.cinema.domain.repository.SeanceRepository;
import lombok.RequiredArgsConstructor;
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
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
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
//    @Test
//    @DisplayName("seance")
//    public void test1() {
//
//        Mockito
//                .when(seanceRepository
//                        .findAll())
//                .thenReturn(seances);
//
//   //     seanceRepository.findAll().forEach(System.out::println);
//
//        BuyingTicketsDTO buyingTicketsDTO = BuyingTicketsDTO.builder()
//                .cityName("LEGNICA")
//                .movieName("ACE VENTURA")
//                .startOfSeance(LocalDateTime.parse("2017-11-09T10:44", DateTimeFormatter.ISO_DATE_TIME))
//                .build();
//
//        Assertions.assertNotNull(seanceService.findByPlaceTitleDate(buyingTicketsDTO));
//    }
}


