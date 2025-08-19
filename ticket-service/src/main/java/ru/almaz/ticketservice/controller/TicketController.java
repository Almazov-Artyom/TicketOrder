package ru.almaz.ticketservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.almaz.ticketservice.dto.TicketDto;
import ru.almaz.ticketservice.dto.TicketFilter;
import ru.almaz.ticketservice.service.TicketService;

import java.util.List;

@RestController
@RequestMapping("/ticket")
@RequiredArgsConstructor
public class TicketController {
    private final TicketService ticketService;

    @GetMapping
    public List<TicketDto> getAvailableTickets(@RequestBody @Valid TicketFilter ticketFilter) {
        return ticketService.getAvailableTickets(ticketFilter);
    }

    @PostMapping("/buy/{ticketId}")
    public void buyTicket(@PathVariable Long ticketId) {
        ticketService.buyTicket(ticketId);
    }
}
