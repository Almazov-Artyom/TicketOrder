package ru.almaz.savedservice.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.almaz.savedservice.entity.Ticket;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class TicketDao {
    private final JdbcTemplate jdbcTemplate;

    private static final String SAVE_SQL = """
                INSERT INTO ticket (route_id, departure_time, seat_number, price, status)
                VALUES (?, ?, ?, ?, ?)
                RETURNING id
            """;

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
}

