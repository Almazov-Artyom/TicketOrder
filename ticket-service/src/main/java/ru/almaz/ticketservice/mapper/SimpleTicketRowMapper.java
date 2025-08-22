package ru.almaz.ticketservice.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.almaz.ticketservice.entity.Route;
import ru.almaz.ticketservice.entity.Ticket;
import ru.almaz.ticketservice.enums.TicketStatus;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class SimpleTicketRowMapper implements RowMapper<Ticket> {
    @Override
    public Ticket mapRow(ResultSet rs, int rowNum) throws SQLException {
        Route route = new Route();
        route.setId(rs.getLong("route_id"));

        Ticket ticket = new Ticket();
        ticket.setId(rs.getLong("id"));
        ticket.setRoute(route);
        ticket.setDepartureTime(rs.getTimestamp("departure_time").toLocalDateTime());
        ticket.setSeatNumber(rs.getString("seat_number"));
        ticket.setPrice(rs.getBigDecimal("price"));
        ticket.setStatus(TicketStatus.valueOf(rs.getString("status")));

        return ticket;

    }
}
