package com.cinema.infrastructure.controller;

import com.cinema.application.dto.PlaceDTO;
import com.cinema.application.dto.SeanceDTO;
import com.cinema.application.service.PlaceService;
import com.cinema.application.service.SeanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/seance")
public class SeanceController {
    private final SeanceService seanceService;

    @GetMapping("{id}")
    public SeanceDTO findOne(@PathVariable Long id) {
        return seanceService
                .findOne(id)
                .orElseThrow();
    }

    @GetMapping
    public List<SeanceDTO> findAll() {
        return seanceService.findAll();
    }

    @PostMapping
    public SeanceDTO add(@RequestBody SeanceDTO seanceDTO){
        return seanceService
                .add(seanceDTO)
                .orElseThrow();
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id){
        seanceService.delete(id);
    }
}
