package ru.almaz.ticketservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.almaz.ticketservice.dto.ticket.*;
import ru.almaz.ticketservice.service.TicketService;

import java.util.List;

@RestController
@RequestMapping("/ticket")
@RequiredArgsConstructor
public class TicketController {
    private final TicketService ticketService;

    @GetMapping
    public List<TicketInfo> getAvailableTickets(@RequestBody @Valid TicketFilter ticketFilter) {
        return ticketService.getAvailableTickets(ticketFilter);
    }

    @PostMapping("/buy/{ticketId}")
    public void buyTicket(@PathVariable Long ticketId) {
        ticketService.buyTicket(ticketId);
    }

    @GetMapping("/purchased")
    public List<TicketInfo> getPurchasedTickets() {
        return ticketService.getAllTicketsByUser();
    }

    @PostMapping
    public TicketDto addTicket(@RequestBody @Valid AddTicketRequest ticketRequest) {
        return ticketService.saveTicket(ticketRequest);
    }

    @PatchMapping("/{ticketId}")
    public TicketDto updateTicket(@PathVariable Long ticketId,
                                  @RequestBody @Valid UpdateTicketRequest ticketRequest) {
        return ticketService.updateTicket(ticketId, ticketRequest);
    }

    @DeleteMapping("/{ticketId}")
    public void deleteTicket(@PathVariable Long ticketId) {
        ticketService.deleteTicket(ticketId);
    }
}
