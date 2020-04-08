package com.cinema.infrastructure.controller;

import com.cinema.application.dto.PlaceDTO;
import com.cinema.application.service.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/place")
public class PlaceController {

    private final PlaceService placeService;

    @GetMapping("{id}")
    public PlaceDTO findOne(@PathVariable Long id) {
        return placeService
                .findOne(id)
                .orElseThrow();
    }

    @GetMapping
    public List<PlaceDTO> findAll() {
        return placeService.findAll();
    }

    @PostMapping
    public PlaceDTO add(@RequestBody PlaceDTO placeDTO) {
        return placeService
                .add(placeDTO)
                .orElseThrow();
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        placeService.delete(id);
    }
}
