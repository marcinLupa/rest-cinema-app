package com.cinema;

import com.cinema.application.dto.*;
import com.cinema.application.dto.enums.FilteringOption;
import com.cinema.application.dto.mapers.Mapper;
import com.cinema.application.service.*;
import com.cinema.domain.model.enums.Genre;
import com.cinema.domain.model.enums.Discount;
import com.cinema.domain.model.enums.Role;
import com.cinema.domain.repository.TicketRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.test.context.event.annotation.BeforeTestMethod;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class TicketServiceTests {

    @Mock
    private UserService userService;
    @Mock
    private SeanceService seanceService;
    @Mock
    private TicketRepository ticketRepository;
    @InjectMocks
    private TicketService ticketService;


    private static Optional<UserDTO> getFirstUserDTOtoTest() {
        return Optional.of(UserDTO.builder()
                .name("imie1")
                .surname("nazwisko1")
                .role(Role.USER)
                .email("email1@email.com")
                .age(18)
                .build());
    }

    private Optional<UserDTO> getSecondUserDTOtoTest() {
        return Optional.of(UserDTO.builder()
                .name("imie2")
                .surname("nazwisko2")
                .role(Role.USER)
                .email("email2@email.com")
                .age(18)
                .build());
    }

    private BuyingTicketsDTO getBuyingTicketDTOtoTest() {
        return BuyingTicketsDTO.builder()
                .cityName("LEGNICA")
                .movieName("ACE VENTURA")
                .startOfSeance(LocalDateTime.parse("2017-11-09T10:44", DateTimeFormatter.ISO_DATE_TIME))
                .email("email@email.com")
                .discounts(EnumSet.of(Discount.GRUPOWY, Discount.NOCNE_KINO, Discount.GRUPOWY))
                .ticketQuantity(1)
                .build();
    }


    private Optional<SeanceDTO> getFirstSeanceDTOtoTest() {
        return Optional.of(SeanceDTO
                .builder()
                .placeDTO(PlaceDTO.builder()
                        .name("WROCLAW")
                        .build())
                .movieDTO(MovieDTO.builder()
                        .title("RAMBO")
                        .genre(Genre.COMEDY)
                        .duration(120)
                        .build())
                .startOfSeance(LocalDateTime.parse("2020-04-15 22:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .build());
    }

    private Optional<SeanceDTO> getSecondSeanceDTOtoTest() {
        return Optional.of(SeanceDTO
                .builder()
                .placeDTO(PlaceDTO.builder()
                        .name("WROCLAW")
                        .build())
                .movieDTO(MovieDTO.builder()
                        .title("RAMBO")
                        .genre(Genre.ACTION)
                        .duration(100)
                        .build())
                .startOfSeance(LocalDateTime.parse("2020-04-11 21:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .build());
    }

    private Optional<SeanceDTO> getThirdSeanceDTOtoTest() {
        return Optional.of(SeanceDTO
                .builder()
                .placeDTO(PlaceDTO.builder()
                        .name("GDANSK")
                        .build())
                .movieDTO(MovieDTO.builder()
                        .title("MADAGASKAR")
                        .genre(Genre.COMEDY)
                        .duration(90)
                        .build())
                .startOfSeance(LocalDateTime.parse("2020-03-01 22:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .build());
    }

    private List<TicketDTO> getTicketsDTOtoTest() {
        return List.of(
                TicketDTO.builder()
                        .transactionDate(LocalDateTime.of(2020, 3, 2, 13, 30))
                        .discount(Discount.NOCNE_KINO)
                        .price()
                        .userDTO(getFirstUserDTOtoTest().orElseThrow())
                        .seanceDTO(getFirstSeanceDTOtoTest().orElseThrow())
                        .build(),
                TicketDTO.builder()
                        .transactionDate(LocalDateTime.of(2020, 1, 2, 13, 30))
                        .discount(Discount.PORANNY_SEANS)
                        .price()
                        .userDTO(getSecondUserDTOtoTest().orElseThrow())
                        .seanceDTO(getSecondSeanceDTOtoTest().orElseThrow())
                        .build(),
                TicketDTO.builder()
                        .transactionDate(LocalDateTime.of(2020, 4, 2, 13, 30))
                        .discount(Discount.PORANNY_SEANS)
                        .price()
                        .userDTO(getSecondUserDTOtoTest().orElseThrow())
                        .seanceDTO(getThirdSeanceDTOtoTest().orElseThrow())
                        .build());

    }

    private List<HistoryDTO> getHistoriesDTOtoTest() {
        return List.of(
                HistoryDTO.builder()
                        .email("email1@email.com")
                        .option(FilteringOption.TITLE)
                        .value("RAMBO")
                        .build(),
                HistoryDTO.builder()
                        .email("email2@email.com")
                        .option(FilteringOption.BY_DISCOUNT)
                        .value("SENIOR")
                        .build(),
                HistoryDTO.builder()
                        .email("email2@email.com")
                        .option(FilteringOption.TITLE)
                        .value("RAMBO")
                        .build(),
                HistoryDTO.builder()
                        .email("email2@email.com")
                        .option(FilteringOption.BY_PRICE)
                        .value("20")
                        .build());
    }

//    @Test
//    @DisplayName("tickets buyingTicketDTO method")
//    public void test1() {
//
//        BuyingTicketsDTO buyingTicketsDTO = getBuyingTicketDTOtoTest();
//
//        Mockito
//                .when(userService
//                        .findByEmail(buyingTicketsDTO.getEmail()))
//                .thenReturn(getSecondUserDTOtoTest());
//        Mockito
//                .when(userService.add(UserDTO.builder()
//                        .email(buyingTicketsDTO.getEmail())
//                        .build()))
//                .thenReturn(getSecondUserDTOtoTest());
//
//        Mockito
//                .when(seanceService.findByPlaceTitleDate(buyingTicketsDTO))
//                .thenReturn(getFirstSeanceDTOtoTest());
//
//
//        Assertions.assertNotNull(
//                ticketService.buyingTickets(buyingTicketsDTO));
//    }

//    @Test
//    @DisplayName("testing builder function")
//    public void test2() {
//
//        System.out.println(TicketDTO.builder()
//                .seanceDTO(getFirstSeanceDTOtoTest().orElseThrow())
//                .discount(Discount.NORMALNY)
//                .userDTO(getFirstUserDTOtoTest().orElseThrow())
//                .price()
//
//                .build());
//
//        System.out.println(new TicketDTO().getPrice());
//
//    }

//    @Test
//    @DisplayName("testing getHistory method - findByUser")
//    public void test2() {
//        String email = "email1@email.com";
//        Mockito
//                .when(ticketRepository.findByUser(email))
//                .thenReturn(getTicketsDTOtoTest()
//                        .stream()
//                        .filter(x -> x.getUserDTO().getEmail().equals(email))
//                        .map(Mapper::fromTicketDTOtoTicket)
//                        .collect(Collectors.toList()));
//
//
//       ticketService.findAll().forEach(System.out::println);
//
//        System.out.println("----");
//        ticketService
//                .getHistory(getHistoriesDTOtoTest().get(0))
//                .forEach(System.out::println);
//
//        Assertions.assertNotNull(
//                ticketService.getHistory(getHistoriesDTOtoTest().get(0)));
//
//    }

    @Test
    @DisplayName("testing getHistory method - findAll")
    public void test3() {

        Mockito
                .when(ticketRepository.findAll())
                .thenReturn(getTicketsDTOtoTest()
                        .stream()
                        .map(Mapper::fromTicketDTOtoTicket)
                        .collect(Collectors.toList()));

        ticketService
                .getHistory(getHistoriesDTOtoTest().get(0))
                .forEach(System.out::println);

        Assertions.assertNotNull(
                ticketService.getHistory(getHistoriesDTOtoTest().get(0)));

    }

    @Test
    @DisplayName("testing getHistory method - findByMovieTitle")
    public void test4() {

        Mockito
                .when(ticketRepository.findAll())
                .thenReturn(getTicketsDTOtoTest()
                        .stream()
                        .map(Mapper::fromTicketDTOtoTicket)
                        .collect(Collectors.toList()));


    }

    @Test
    @DisplayName("testing getHistory method - findByDiscount")
    public void test5() {

        Mockito
                .when(ticketRepository.findAll())
                .thenReturn(getTicketsDTOtoTest()
                        .stream()
                        .map(Mapper::fromTicketDTOtoTicket)
                        .collect(Collectors.toList()));


    }
    @Test
    @DisplayName("test")
    public void test6() {

       getTicketsDTOtoTest()
               .forEach(System.out::println);


    }
}

