package ru.almaz.ticketservice.dao;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.almaz.ticketservice.dao.builder.SqlParamsBuilder;
import ru.almaz.ticketservice.dto.ticket.TicketFilter;
import ru.almaz.ticketservice.dto.ticket.UpdateTicketRequest;
import ru.almaz.ticketservice.entity.Ticket;
import ru.almaz.ticketservice.enums.TicketStatus;
import ru.almaz.ticketservice.mapper.row.SimpleTicketRowMapper;
import ru.almaz.ticketservice.mapper.row.TicketRowMapper;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class TicketDao {
    private final JdbcTemplate jdbcTemplate;

    private final SqlParamsBuilder<TicketFilter> sqlTicketFilterBuilder;

    private final TicketRowMapper ticketRowMapper;

    private final SqlParamsBuilder<UpdateTicketRequest> SqlParamsBuilder;

    private final SimpleTicketRowMapper simpleTicketRowMapper;

    private static final String UPDATE_TICKET_STATUS_AND_USER_ID_SQL = """
                WITH updated AS (
                    UPDATE ticket
                    SET status = 'PURCHASED', user_id = ?
                    WHERE id = ? AND status = 'AVAILABLE' AND departure_time > NOW()
                    RETURNING id, route_id, departure_time,seat_number, price, status
                )
                SELECT
                    t.id AS ticket_id,
                    r.id AS route_id,
                    r.origin AS route_origin,
                    r.destination AS route_destination,
                    c.id AS carrier_id,
                    c.name AS carrier_name,
                    c.phone_number AS carrier_phone_number,
                    r.duration AS route_duration,
                    t.departure_time AS ticket_departure_time,
                    t.seat_number AS ticket_seat_number,
                    t.price AS ticket_price,
                    t.status AS ticket_status
                FROM updated as t
                JOIN route as r ON r.id = t.route_id
                JOIN carrier as c ON c.id = r.carrier_id
            """;

    private static final String FIND_ALL_TICKETS_BY_USER_ID = """
                SELECT
                    t.id AS ticket_id,
                    r.id AS route_id,
                    r.origin AS route_origin,
                    r.destination AS route_destination,
                    c.id AS carrier_id,
                    c.name AS carrier_name,
                    c.phone_number AS carrier_phone_number,
                    r.duration AS route_duration,
                    t.departure_time AS ticket_departure_time,
                    t.seat_number AS ticket_seat_number,
                    t.price AS ticket_price,
                    t.status AS ticket_status
                FROM ticket AS t
                JOIN route AS r ON t.route_id = r.id
                JOIN carrier AS c ON r.carrier_id = c.id
                WHERE t.user_id = ?
            """;

    private static final String SAVE_SQL = """
                INSERT INTO ticket (route_id, departure_time, seat_number, price, status)
                VALUES (?, ?, ?, ?, ?)
                RETURNING id
            """;

    private static final String EXIST_TICKET_SQL = """
                SELECT EXISTS(
                    SELECT *
                    FROM ticket
                    WHERE id = ?
                )
            """;

    private static final String DELETE_SQL = """
                DELETE FROM ticket
                WHERE id = ?
            """;

    private static final String UPDATE_STATUS_TICKET_BY_TIME_SQL = """
                UPDATE ticket
                SET status = 'EXPIRED'
                WHERE departure_time < NOW() AND status = 'AVAILABLE'
            """;

    public List<Ticket> findAllAvailableTickets(TicketFilter ticketFilter) {
        Map.Entry<String, List<Object>> sqlAndParams = sqlTicketFilterBuilder.buildSqlAndParams(ticketFilter);

        String sql = sqlAndParams.getKey();
        List<Object> params = sqlAndParams.getValue();

        return jdbcTemplate.query(sql, ticketRowMapper, params.toArray());
    }

    public Ticket updateTicketStatusAndUserId(Long userId, Long ticketId) {
        return jdbcTemplate.queryForObject
                (UPDATE_TICKET_STATUS_AND_USER_ID_SQL,ticketRowMapper, userId, ticketId);
    }

    public List<Ticket> findAllTicketsByUserId(Long userId) {
        return jdbcTemplate.query(FIND_ALL_TICKETS_BY_USER_ID, ticketRowMapper, userId);
    }

    public Ticket save(Ticket ticket) {
        KeyHolder generatedKeyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(SAVE_SQL, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setLong(1, ticket.getRoute().getId());
            ps.setTimestamp(2, ticket.getDepartureTime());
            ps.setString(3, ticket.getSeatNumber());
            ps.setBigDecimal(4, ticket.getPrice());
            ps.setString(5, ticket.getStatus().name());
            return ps;
        }, generatedKeyHolder);

        ticket.setId((Long) generatedKeyHolder.getKey());
        return ticket;
    }

    public Ticket updateTicket(Long ticketId, UpdateTicketRequest updateTicketRequest) {
        Map.Entry<String, List<Object>> sqlAndParams = SqlParamsBuilder.buildSqlAndParams(updateTicketRequest);
        String sql = sqlAndParams.getKey() + " WHERE id = ? RETURNING id, route_id, departure_time, seat_number, price, status";
        List<Object> params = sqlAndParams.getValue();
        params.add(ticketId);

        return jdbcTemplate.queryForObject(sql, simpleTicketRowMapper, params.toArray());
    }

    public boolean existTicket(Long ticketId) {
        Boolean exists = jdbcTemplate.queryForObject(EXIST_TICKET_SQL, Boolean.class, ticketId);
        return Boolean.TRUE.equals(exists);
    }

    public void deleteTicket(Long ticketId) {
        jdbcTemplate.update(DELETE_SQL, ticketId);
    }

    public void updateTicketStatusOnExpired(){
        jdbcTemplate.update(UPDATE_STATUS_TICKET_BY_TIME_SQL);
    }
}

