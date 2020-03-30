package com.cinema.infrastructure.controller;

import com.cinema.application.dto.FilteringMoviesDTO;
import com.cinema.application.dto.MovieDTO;
import com.cinema.application.dto.enums.FilteringOption;
import com.cinema.application.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/movie")
public class MovieController {

    private final MovieService movieService;

    @GetMapping("{id}")
    public MovieDTO findOne(@PathVariable Long id) {
        return movieService
                .findOne(id)
                .orElseThrow();
    }

    @GetMapping
    public List<MovieDTO> findAll() {
        return movieService.findAll();
    }

    @PostMapping
    public MovieDTO add(@RequestBody MovieDTO movieDTO){
        return movieService
                .add(movieDTO)
                .orElseThrow();
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id){
        movieService.delete(id);
    }

    @PostMapping("/filtering")
    public List<MovieDTO> filteringMovies(@RequestBody FilteringMoviesDTO filteringMoviesDTO){
        return movieService.filteringMovies(filteringMoviesDTO);
    }
}