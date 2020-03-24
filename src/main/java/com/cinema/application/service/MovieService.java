package com.cinema.application.service;

import com.cinema.application.dto.MovieDTO;
import com.cinema.application.dto.UserDTO;
import com.cinema.application.dto.mapers.Mapper;
import com.cinema.domain.model.Movie;
import com.cinema.domain.repository.MovieRepository;
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
public class MovieService {

    private final MovieRepository movieRepository;

    public Optional<MovieDTO> findOne(Long id) {
        if (id == null) {
            throw new AppException("FIND ONE MOVIE EXCEPTION");
        }
        return Optional.of(Mapper.fromMovieToMovieDTO(movieRepository
                .findOne(id)
                .orElseThrow()));
    }

    public List<MovieDTO> findAll() {
        return movieRepository
                .findAll()
                .stream()
                .map(Mapper::fromMovieToMovieDTO)
                .collect(Collectors.toList());
    }

    public Optional<MovieDTO> add(MovieDTO movieDTO) {
        if(movieDTO==null){
            throw new AppException("MOVIE DTO IS NULL");
        }
        return Optional.of(Mapper.fromMovieToMovieDTO(movieRepository
                .save(Mapper.fromMovieDTOtoMovie(movieDTO))
                .orElseThrow()));
    }

    public void delete(Long id) {
        if(id==null){
            throw new AppException("DELETE MOVIE ID IS NULL");
        }
        movieRepository.delete(id);
    }

}
