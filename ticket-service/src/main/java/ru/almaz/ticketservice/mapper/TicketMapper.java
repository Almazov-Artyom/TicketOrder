package ru.almaz.ticketservice.mapper;

import org.springframework.stereotype.Component;
import ru.almaz.ticketservice.dto.TicketDto;
import ru.almaz.ticketservice.entity.Carrier;
import ru.almaz.ticketservice.entity.Route;
import ru.almaz.ticketservice.entity.Ticket;

@Component
public class TicketMapper {
    public TicketDto toDto(Ticket ticket) {
        Route route = ticket.getRoute();
        Carrier carrier = route.getCarrier();
        String duration = (route.getDuration().toMinutes()) + " min";
        return new TicketDto(
                ticket.getId(), route.getOrigin(), route.getDestination(), carrier.getName(), duration,
                ticket.getDepartureTime(), ticket.getSeatNumber(), ticket.getPrice()
        );
    }
}
