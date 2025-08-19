package ru.almaz.ticketservice.dao;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.almaz.ticketservice.dao.builder.SqlTicketFilterBuilder;
import ru.almaz.ticketservice.dto.TicketFilter;
import ru.almaz.ticketservice.entity.Carrier;
import ru.almaz.ticketservice.entity.Route;
import ru.almaz.ticketservice.entity.Ticket;
import ru.almaz.ticketservice.enums.TicketStatus;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class TicketDao {
    private final JdbcTemplate jdbcTemplate;

    private final SqlTicketFilterBuilder<TicketFilter> sqlTicketFilterBuilder;

    private static final String CREATE_TABLE_SQL = """
                CREATE TABLE IF NOT EXISTS ticket (
                    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                    route_id BIGINT,
                    departure_time TIMESTAMP(0) NOT NULL,
                    seat_number varchar(50) NOT NULL,
                    price NUMERIC(10,2) NOT NULL,
                    status varchar(50) NOT NULL CHECK ( status IN ('AVAILABLE','PURCHASED')),
                    FOREIGN KEY (route_id) REFERENCES route(id) ON DELETE SET NULL
                )
            """;

    private static final String EXIST_TICKET_FOR_PURCHASE_SQL = """
                SELECT EXISTS(
                    SELECT *
                    FROM ticket
                    WHERE id = ? AND departure_time >= NOW() AND status = 'AVAILABLE'
                )
            """;

    private static final String UPDATE_TICKET_STATUS_SQL = """
                UPDATE ticket
                SET status = 'PURCHASED'
                WHERE id = ?
            """;

    @PostConstruct
    public void init() {
        jdbcTemplate.execute(CREATE_TABLE_SQL);
    }

    public List<Ticket> findAllAvailableTickets(TicketFilter ticketFilter) {
        Map.Entry<String, List<Object>> sqlAndParams = sqlTicketFilterBuilder.buildSqlAndParams(ticketFilter);
        String sql = sqlAndParams.getKey();
        List<Object> params = sqlAndParams.getValue();

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
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

                }, params.toArray()
        );
    }

    public boolean existTicketForPurchase(Long ticketId) {
        Boolean exists = jdbcTemplate.queryForObject(EXIST_TICKET_FOR_PURCHASE_SQL, Boolean.class, ticketId);
        return Boolean.TRUE.equals(exists);
    }

    public void updateTicketStatus(Long ticketId) {
        jdbcTemplate.update(UPDATE_TICKET_STATUS_SQL, ticketId);
    }
}

