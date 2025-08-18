package ru.almaz.ticketservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.almaz.ticketservice.dao.TicketDao;
import ru.almaz.ticketservice.dto.TicketDto;
import ru.almaz.ticketservice.dto.TicketFilter;
import ru.almaz.ticketservice.entity.Carrier;
import ru.almaz.ticketservice.entity.Route;
import ru.almaz.ticketservice.entity.Ticket;
import ru.almaz.ticketservice.validator.TicketFilterValidator;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketService {
    private final TicketDao ticketDao;

    private final TicketFilterValidator ticketFilterValidator;

    public List<TicketDto> getAvailableTickets(TicketFilter ticketFilter) {
        ticketFilterValidator.dateValidation(ticketFilter);

        List<Ticket> allAvailableTickets = ticketDao.findAllAvailableTickets(ticketFilter);

        return allAvailableTickets.stream().map(ticket -> {
            Route route = ticket.getRoute();
            Carrier carrier = route.getCarrier();
            String duration = route.getDuration().toString() + " min";
            return new TicketDto(
                    route.getOrigin(), route.getDestination(), carrier.getName(), duration,
                    ticket.getDepartureTime(), ticket.getSeatNumber(), ticket.getPrice()
            );
        }).toList();
    }
}
