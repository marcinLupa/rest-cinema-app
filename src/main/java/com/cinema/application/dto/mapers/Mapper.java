package com.cinema.application.dto.mapers;

import com.cinema.application.dto.*;
import com.cinema.domain.model.*;

public interface Mapper {
    static MovieDTO fromMovieToMovieDTO(Movie movie) {
        return movie == null ? null : MovieDTO
                .builder()
                .id(movie.getId())
                .duration(movie.getDuration())
                .genre(movie.getGenre())
                .title(movie.getTitle())
                .build();
    }

    static UserDTO fromUserToUserDTO(User user) {
        return user == null ? null : UserDTO
                .builder()
                .id(user.getId())
                .age(user.getAge())
                .name(user.getName())
                .role(user.getRole())
                .surname(user.getSurname())
                .build();
    }

    static PlaceDTO fromPlaceToPlaceDTO(Place place) {
        return place == null ? null : PlaceDTO
                .builder()
                .id(place.getId())
                .name(place.getName())
                .build();
    }

    static SeanceDTO fromSeanceToSeanceDTO(Seance seance) {
        return seance == null ? null : SeanceDTO
                .builder()
                .id(seance.getId())
                .movieDTO(fromMovieToMovieDTO(seance.getMovie()))
                .placeDTO(fromPlaceToPlaceDTO(seance.getPlace()))
                .startOfSeance(seance.getStartOfSeance())
                .build();
    }

    static TicketDTO fromTicketToTicketDTO(Ticket ticket) {
        return ticket == null ? null : TicketDTO
                .builder()
                .id(ticket.getId())
                .discount(ticket.getDiscount())
                .kindOfTicket(ticket.getKindOfTicket())
                .price(ticket.getPrice())
                .quantity(ticket.getQuantity())
                .seanceDTO(fromSeanceToSeanceDTO(ticket.getSeance()))
                .userDTO(fromUserToUserDTO(ticket.getUser()))
                .build();
    }

    static Movie fromMovieDTOtoMovie(MovieDTO movieDTO) {
        return movieDTO == null ? null : Movie
                .builder()
                .id(movieDTO.getId())
                .duration(movieDTO.getDuration())
                .genre(movieDTO.getGenre())
                .title(movieDTO.getTitle())
                .build();
    }

    static User fromUserDTOtoUser(UserDTO userDTO) {
        return userDTO == null ? null : User
                .builder()
                .id(userDTO.getId())
                .age(userDTO.getAge())
                .name(userDTO.getName())
                .role(userDTO.getRole())
                .surname(userDTO.getSurname())
                .build();
    }

    static Place fromPlaceDTOtoPlace(PlaceDTO placeDTO) {
        return placeDTO == null ? null : Place
                .builder()
                .id(placeDTO.getId())
                .name(placeDTO.getName())
                .build();
    }

    static Seance fromSeanceDTOtoSeance(SeanceDTO seanceDTO) {
        return seanceDTO == null ? null : Seance
                .builder()
                .id(seanceDTO.getId())
                .movie(fromMovieDTOtoMovie(seanceDTO.getMovieDTO()))
                .place(fromPlaceDTOtoPlace(seanceDTO.getPlaceDTO()))
                .startOfSeance(seanceDTO.getStartOfSeance())
                .build();
    }

    static Ticket fromTicketDTOtoTicket(TicketDTO ticketDTO) {
        return ticketDTO == null ? null : Ticket
                .builder()
                .id(ticketDTO.getId())
                .discount(ticketDTO.getDiscount())
                .kindOfTicket(ticketDTO.getKindOfTicket())
                .price(ticketDTO.getPrice())
                .quantity(ticketDTO.getQuantity())
                .seance(fromSeanceDTOtoSeance(ticketDTO.getSeanceDTO()))
                .user(fromUserDTOtoUser(ticketDTO.getUserDTO()))
                .build();
    }
}
