package com.cinema.infrastructure.controller;

import com.cinema.application.service.SeanceService;
import com.cinema.domain.model.Seance;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/seance")
public class SeanceController {
    private final SeanceService seanceService;

    @GetMapping("{id}")
    public Seance findOne(@PathVariable Long id) {
        return seanceService
                .findOne(id)
                .orElseThrow();
    }

    @GetMapping
    public List<Seance> findAll() {
        return seanceService.findAll();
    }

    @PostMapping
    public Seance add(@RequestBody Seance seance) {
        return seanceService
                .add(seance)
                .orElseThrow();
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        seanceService.delete(id);
    }
}
