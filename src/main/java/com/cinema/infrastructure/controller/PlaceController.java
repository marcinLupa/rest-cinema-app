package com.cinema.infrastructure.controller;

import com.cinema.application.service.PlaceService;
import com.cinema.domain.model.Place;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/place")
public class PlaceController {

    private final PlaceService placeService;

    @GetMapping("{id}")
    public Place findOne(@PathVariable Long id) {
        return placeService
                .findOne(id)
                .orElseThrow();
    }

    @GetMapping
    public List<Place> findAll() {
        return placeService.findAll();
    }

    @PostMapping
    public Place add(@RequestBody Place place) {
        return placeService
                .add(place)
                .orElseThrow();
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        placeService.delete(id);
    }
}
