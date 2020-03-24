package com.cinema.application.service;

import com.cinema.application.dto.PlaceDTO;
import com.cinema.application.dto.UserDTO;
import com.cinema.application.dto.mapers.Mapper;
import com.cinema.domain.repository.MovieRepository;
import com.cinema.domain.repository.PlaceRepository;
import com.cinema.infrastructure.exceptions.AppException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PlaceService {

    private final PlaceRepository placeRepository;

    public Optional<PlaceDTO> findOne(Long id) {
        if (id == null) {
            throw new AppException("FIND ONE PLACE EXCEPTION");
        }
        return Optional.of(Mapper.fromPlaceToPlaceDTO(placeRepository
                .findOne(id)
                .orElseThrow()));
    }

    public List<PlaceDTO> findAll() {
        return placeRepository
                .findAll()
                .stream()
                .map(Mapper::fromPlaceToPlaceDTO)
                .collect(Collectors.toList());
    }

    public Optional<PlaceDTO> add(PlaceDTO placeDTO) {
        if (placeDTO == null) {
            throw new AppException("PLACE DTO ADD EXCEPTION");
        }
        return Optional.of(Mapper.fromPlaceToPlaceDTO(placeRepository
                .save(Mapper.fromPlaceDTOtoPlace(placeDTO))
                .orElseThrow()));
    }

    public void delete(Long id) {
        if (id == null) {
            throw new AppException("DELETE PLACE ID EXCEPTION");
        }
        placeRepository.delete(id);
    }

}
