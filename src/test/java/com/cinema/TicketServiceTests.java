package com.cinema;

import com.cinema.application.dto.*;
import com.cinema.application.service.*;
import com.cinema.domain.model.enums.Genre;
import com.cinema.domain.model.enums.Discount;
import com.cinema.domain.model.enums.Role;
import com.cinema.domain.repository.TicketRepository;
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
import java.util.EnumSet;
import java.util.Optional;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class TicketServiceTests {

    @Mock
    private UserService userService;

    @Mock
    private SeanceService seanceService;
    @Mock
    private PlaceService placeService;
    @Mock
    private MovieService movieService;

    private TicketRepository ticketRepository;

    @InjectMocks
    private TicketService ticketService;

    @Test
    @DisplayName("tickets")
    public void test1() {

        BuyingTicketsDTO buyingTicketsDTO = BuyingTicketsDTO.builder()
                .cityName("LEGNICA")
                .movieName("ACE VENTURA")
                .startOfSeance(LocalDateTime.parse("2017-11-09T10:44", DateTimeFormatter.ISO_DATE_TIME))
                .email("email@email.com")
                .discounts(EnumSet.of(Discount.NORMALNY, Discount.NOCNE_KINO, Discount.GRUPOWY))
                .ticketQuantity(3)
                .build();

        Mockito
                .when(userService
                        .findByEmail(buyingTicketsDTO.getEmail()))
                .thenReturn(Optional.of(UserDTO.builder()
                        .name("imie1")
                        .surname("nazwisko1")
                        .role(Role.USER)
                        .email("email@email.com")
                        .age(18)
                        .build()));
        Mockito
                .when(userService.add(UserDTO.builder()
                        .email(buyingTicketsDTO.getEmail())
                        .build()))
                .thenReturn(Optional.of(UserDTO.builder()
                        .name("imie2")
                        .surname("nazwisko2")
                        .role(Role.USER)
                        .email("email@email.com")
                        .age(18)
                        .build()));

        Mockito
                .when(seanceService.findByPlaceTitleDate(buyingTicketsDTO))
                .thenReturn(Optional.of(SeanceDTO
                        .builder()
                        .placeDTO(PlaceDTO.builder()
                                .name("MIASTO")
                                .build())
                        .movieDTO(MovieDTO.builder()
                                .title("TYTUL")
                                .genre(Genre.COMEDY)
                                .duration(120)
                                .build())
                        .startOfSeance(LocalDateTime.parse("2020-04-15 22:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                        .build()));

        ticketService
                .buyingTickets(buyingTicketsDTO)
                .forEach(System.out::println);

        Assertions.assertNotNull(
                ticketService.buyingTickets(buyingTicketsDTO));
    }
}
