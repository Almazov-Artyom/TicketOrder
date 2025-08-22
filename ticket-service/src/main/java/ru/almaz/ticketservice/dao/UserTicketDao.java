package ru.almaz.ticketservice.dao;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.almaz.ticketservice.entity.Ticket;
import ru.almaz.ticketservice.mapper.row.TicketRowMapper;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserTicketDao {

    private final JdbcTemplate jdbcTemplate;

    private final TicketRowMapper ticketRowMapper;

    private static final String CREATE_TABLE_SQL = """
                CREATE TABLE IF NOT EXISTS user_ticket (
                    user_id BIGINT,
                    ticket_id BIGINT,
                    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE SET NULL,
                    FOREIGN KEY (ticket_id) REFERENCES ticket(id) ON DELETE SET NULL
                )
            """;

    private static final String SAVE_SQL = """
                INSERT INTO user_ticket (user_id, ticket_id) VALUES (?, ?)
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
                FROM user_ticket AS ut
                JOIN ticket AS t ON ut.ticket_id = t.id
                JOIN route AS r ON t.route_id = r.id
                JOIN carrier AS c ON r.carrier_id = c.id
                WHERE ut.user_id = ?
            """;

    @PostConstruct
    public void init() {
        jdbcTemplate.execute(CREATE_TABLE_SQL);
    }

    public void save(Long userId, Long ticketId) {
        jdbcTemplate.update(SAVE_SQL, userId, ticketId);
    }

    public List<Ticket> findAllTicketsByUserId(Long userId) {
        return jdbcTemplate.query(FIND_ALL_TICKETS_BY_USER_ID, ticketRowMapper,userId);
    }


}
