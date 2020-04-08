package com.cinema.infrastructure.controller;

import com.cinema.application.dto.BuyingTicketsDTO;
import com.cinema.application.dto.HistoryDTO;
import com.cinema.application.dto.TicketDTO;
import com.cinema.application.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ticket")
public class TicketController {
    private final TicketService ticketService;

    @GetMapping("{id}")
    public TicketDTO findOne(@PathVariable Long id) {
        return ticketService
                .findOne(id)
                .orElseThrow();
    }

    @GetMapping
    public List<TicketDTO> findAll() {
        return ticketService.findAll();
    }

    @PostMapping
    public TicketDTO add(@RequestBody TicketDTO ticketDTO) {
        return ticketService
                .add(ticketDTO)
                .orElseThrow();
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        ticketService.delete(id);
    }

    @PostMapping("/buy")
    public List<TicketDTO> buyingTickets(@RequestBody BuyingTicketsDTO buyingTicketsDTO) {
        return ticketService
                .buyingTickets(buyingTicketsDTO);
    }

    @PostMapping("/history")
    public List<TicketDTO> getHistory(@RequestBody HistoryDTO historyDTO) {
        return ticketService
                .getHistory(historyDTO);
    }
}
