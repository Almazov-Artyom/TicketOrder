package ru.almaz.savedservice.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.almaz.savedservice.entity.TicketInfo;

import java.sql.PreparedStatement;

@Repository
@RequiredArgsConstructor
public class TicketDao {

    private final JdbcTemplate jdbcTemplate;

    private static final String SAVE_SQL = """
                INSERT INTO ticket_info (ticket_id, origin, destination, carrier_name, duration, departure_time, seat_number, price)
                VALUES(?, ?, ?, ?, ?, ?, ?, ?)
            """;

    public void save(TicketInfo ticketInfo) {
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(SAVE_SQL);

            ps.setLong(1, ticketInfo.getTicketId());
            ps.setString(2, ticketInfo.getOrigin());
            ps.setString(3, ticketInfo.getDestination());
            ps.setString(4, ticketInfo.getCarrierName());
            ps.setInt(5, ticketInfo.getDuration());
            ps.setTimestamp(6, ticketInfo.getDepartureTime());
            ps.setString(7, ticketInfo.getSeatNumber());
            ps.setBigDecimal(8, ticketInfo.getPrice());

            return ps;
        });
    }
}
