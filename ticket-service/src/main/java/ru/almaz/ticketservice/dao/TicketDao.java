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

    private static final String SAVE_SQL = """
                INSERT INTO ticket (route_id, departure_time, seat_number, price, status)
                VALUES (?, ?, ?, ?, ?)
                RETURNING id
            """;

    @PostConstruct
    public void init() {
        jdbcTemplate.execute(CREATE_TABLE_SQL);
        UpdateTicketRequest updateTicketRequest = new UpdateTicketRequest(null, null, null, null, TicketStatus.AVAILABLE.name());
        updateTicket(4L,updateTicketRequest);
    }

    public List<Ticket> findAllAvailableTickets(TicketFilter ticketFilter) {
        Map.Entry<String, List<Object>> sqlAndParams = sqlTicketFilterBuilder.buildSqlAndParams(ticketFilter);

        String sql = sqlAndParams.getKey();
        List<Object> params = sqlAndParams.getValue();

        return jdbcTemplate.query(sql, ticketRowMapper, params.toArray());
    }

    public boolean existTicketForPurchase(Long ticketId) {
        Boolean exists = jdbcTemplate.queryForObject(EXIST_TICKET_FOR_PURCHASE_SQL, Boolean.class, ticketId);
        return Boolean.TRUE.equals(exists);
    }

    public void updateTicketStatus(Long ticketId) {
        jdbcTemplate.update(UPDATE_TICKET_STATUS_SQL, ticketId);
    }

    public Ticket save(Ticket ticket) {
        KeyHolder generatedKeyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(SAVE_SQL, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setLong(1, ticket.getRoute().getId());
            ps.setTimestamp(2, Timestamp.valueOf(ticket.getDepartureTime()));
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
}

