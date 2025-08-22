package ru.almaz.ticketservice.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.almaz.ticketservice.entity.Carrier;
import ru.almaz.ticketservice.entity.Route;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;

@Component
public class RouteRowMapper implements RowMapper<Route> {
    @Override
    public Route mapRow(ResultSet rs, int rowNum) throws SQLException {
        Carrier carrier = new Carrier();
        carrier.setId(rs.getLong("carrier_id"));

        Route route = new Route();
        route.setId(rs.getLong("id"));
        route.setOrigin(rs.getString("origin"));
        route.setDestination(rs.getString("destination"));
        route.setCarrier(carrier);
        route.setDuration(Duration.ofMinutes(rs.getInt("duration")));

        return route;
    }
}
