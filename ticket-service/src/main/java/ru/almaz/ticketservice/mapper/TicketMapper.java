package ru.almaz.ticketservice.mapper;

import org.springframework.stereotype.Component;
import ru.almaz.ticketservice.dto.ticket.TicketDto;
import ru.almaz.ticketservice.dto.ticket.AddTicketRequest;
import ru.almaz.ticketservice.dto.ticket.TicketResponse;
import ru.almaz.ticketservice.entity.Carrier;
import ru.almaz.ticketservice.entity.Route;
import ru.almaz.ticketservice.entity.Ticket;
import ru.almaz.ticketservice.enums.TicketStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class TicketMapper {
    public TicketDto toDto(Ticket ticket) {
        Route route = ticket.getRoute();
        Carrier carrier = route.getCarrier();
        String duration = route.getDuration() + " min";
        return new TicketDto(
                ticket.getId(), route.getOrigin(), route.getDestination(), carrier.getName(), duration,
                ticket.getDepartureTime(), ticket.getSeatNumber(), ticket.getPrice()
        );
    }

    public Ticket toTicket(AddTicketRequest ticketRequest) {
        Route route = new Route();
        route.setId(ticketRequest.routeId());

        Ticket ticket = new Ticket();
        ticket.setRoute(route);
        ticket.setDepartureTime(ticketRequest.departureTime());
        ticket.setSeatNumber(ticketRequest.seatNumber());
        ticket.setPrice(ticketRequest.price());

        return ticket;
    }

    public TicketResponse toTicketResponse(Ticket ticket) {
        Long ticketId = ticket.getId();
        Long routeId = ticket.getRoute().getId();
        LocalDateTime departureTime = ticket.getDepartureTime();
        String seatNumber = ticket.getSeatNumber();
        BigDecimal price = ticket.getPrice();
        TicketStatus status = ticket.getStatus();

        return new TicketResponse(ticketId, routeId, departureTime, seatNumber, price, status);
    }
}
