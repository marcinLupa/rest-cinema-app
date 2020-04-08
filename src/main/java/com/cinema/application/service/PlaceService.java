package com.cinema.application.service;

import com.cinema.application.dto.PlaceDTO;
import com.cinema.application.dto.mapers.Mapper;
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
    /**
     * @role Role.ADMIN
     **/
    public Optional<PlaceDTO> findOne(Long id) {
        if (id == null) {
            throw new AppException("FIND ONE PLACE EXCEPTION");
        }
        return Optional.of(Mapper.fromPlaceToPlaceDTO(placeRepository
                .findOne(id)
                .orElseThrow()));
    }
    /**
     * @role Role.ADMIN
     **/
    public List<PlaceDTO> findAll() {
        return placeRepository
                .findAll()
                .stream()
                .map(Mapper::fromPlaceToPlaceDTO)
                .collect(Collectors.toList());
    }
    /**
     * @role Role.ADMIN
     **/
    public Optional<PlaceDTO> add(PlaceDTO placeDTO) {
        if (placeDTO == null) {
            throw new AppException("PLACE DTO ADD EXCEPTION");
        }
        return Optional.of(Mapper.fromPlaceToPlaceDTO(placeRepository
                .save(Mapper.fromPlaceDTOtoPlace(placeDTO))
                .orElseThrow()));
    }
    /**
     * @role Role.ADMIN
     **/
    public void delete(Long id) {
        if (id == null) {
            throw new AppException("DELETE PLACE ID EXCEPTION");
        }
        placeRepository.delete(id);
    }

}
