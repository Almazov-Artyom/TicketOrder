package ru.almaz.ticketservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.almaz.ticketservice.dto.ticket.TicketDto;
import ru.almaz.ticketservice.service.UserTicketService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserTicketController {

    private final UserTicketService userTicketService;

    @GetMapping("/purchased-tickets")
    public List<TicketDto> getPurchasedTickets() {
        return userTicketService.getAllTicketsByUser();
    }
}
