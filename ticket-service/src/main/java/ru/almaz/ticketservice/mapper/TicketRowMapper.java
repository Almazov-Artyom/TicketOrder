package ru.almaz.ticketservice.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.almaz.ticketservice.entity.Carrier;
import ru.almaz.ticketservice.entity.Route;
import ru.almaz.ticketservice.entity.Ticket;
import ru.almaz.ticketservice.enums.TicketStatus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;

@Component
public class TicketRowMapper implements RowMapper<Ticket> {

    @Override
    public Ticket mapRow(ResultSet rs, int rowNum) throws SQLException {
        Carrier carrier = new Carrier();
        carrier.setId(rs.getLong("carrier_id"));
        carrier.setName(rs.getString("carrier_name"));
        carrier.setPhoneNumber(rs.getString("carrier_phone_number"));

        Route route = new Route();
        route.setId(rs.getLong("route_id"));
        route.setOrigin(rs.getString("route_origin"));
        route.setDestination(rs.getString("route_destination"));
        route.setCarrier(carrier);
        route.setDuration(Duration.ofMinutes(rs.getInt("route_duration")));

        Ticket ticket = new Ticket();
        ticket.setId(rs.getLong("ticket_id"));
        ticket.setRoute(route);
        ticket.setDepartureTime(rs.getTimestamp("ticket_departure_time").toLocalDateTime());
        ticket.setSeatNumber(rs.getString("ticket_seat_number"));
        ticket.setPrice(rs.getBigDecimal("ticket_price"));
        ticket.setStatus(TicketStatus.valueOf(rs.getString("ticket_status")));

        return ticket;
    }
}
