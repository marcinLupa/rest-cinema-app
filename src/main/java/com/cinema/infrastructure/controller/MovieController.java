package com.cinema.infrastructure.controller;

import com.cinema.application.dto.FilteringMoviesDTO;
import com.cinema.application.service.MovieService;
import com.cinema.domain.model.Movie;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/movie")
public class MovieController {

    private final MovieService movieService;

    @GetMapping("{id}")
    public Movie findOne(@PathVariable Long id) {
        return movieService
                .findOne(id)
                .orElseThrow();
    }

    @GetMapping
    public List<Movie> findAll() {
        return movieService.findAll();
    }

    @PostMapping
    public Movie add(@RequestBody Movie movie) {
        return movieService
                .add(movie)
                .orElseThrow();
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        movieService.delete(id);
    }

    @PostMapping("/filtering")

    public List<Movie> filteringMovies(@RequestBody FilteringMoviesDTO filteringMoviesDTO) {
        return movieService
                .getMovies(filteringMoviesDTO);
    }
}
