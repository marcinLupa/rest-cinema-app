package com.cinema.infrastructure.controller;

import com.cinema.application.dto.BuyingTicketsDTO;
import com.cinema.application.dto.HistoryDTO;
import com.cinema.application.service.TicketService;
import com.cinema.domain.model.Ticket;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ticket")
public class TicketController {
    private final TicketService ticketService;

    @GetMapping("{id}")
    public Ticket findOne(@PathVariable Long id) {
        return ticketService
                .findOne(id)
                .orElseThrow();
    }

    @GetMapping
    public List<Ticket> findAll() {
        return ticketService.findAll();
    }

//    @PostMapping
//    public Ticket add(@RequestBody Ticket ticket) {
//        return ticketService
//                .add(ticketDTO)
//                .orElseThrow();
//    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        ticketService.delete(id);
    }

    @PostMapping("/buy")
    public List<Ticket> buyingTickets(@RequestBody BuyingTicketsDTO buyingTicketsDTO) {
        return ticketService
                .buyingTickets(buyingTicketsDTO);
    }

    @PostMapping("/history")
    public List<Ticket> getHistory(@RequestBody HistoryDTO historyDTO) {
        return ticketService
                .getHistory(historyDTO);
    }
}
