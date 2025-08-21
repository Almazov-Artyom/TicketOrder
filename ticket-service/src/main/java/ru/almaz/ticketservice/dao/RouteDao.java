package ru.almaz.ticketservice.dao;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.almaz.ticketservice.entity.Route;

import java.sql.PreparedStatement;

@Repository
@RequiredArgsConstructor
public class RouteDao {
    private final JdbcTemplate jdbcTemplate;

    private static final String CREATE_TABLE_SQL = """
                CREATE TABLE IF NOT EXISTS route (
                    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                    origin VARCHAR(255) NOT NULL,
                    destination VARCHAR(255) NOT NULL,
                    carrier_id BIGINT,
                    duration INT NOT NULL,
                    FOREIGN KEY (carrier_id) REFERENCES carrier(id) ON DELETE SET NULL
                )
            """;

    private static final String SAVE_SQL = """
            INSERT INTO route (origin, destination, carrier_id, duration)
            VALUES (?, ?, ?, ?)
            RETURNING id;
            """;

    @PostConstruct
    public void init() {
        jdbcTemplate.execute(CREATE_TABLE_SQL);
    }

    public Route create(Route route) {
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(SAVE_SQL,PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, route.getOrigin());
            ps.setString(2, route.getDestination());
            ps.setLong(3, route.getCarrier().getId());
            ps.setInt(4, (int) route.getDuration().toMinutes());
            return ps;
        },generatedKeyHolder);

        route.setId((Long) generatedKeyHolder.getKey());

        return route;
    }
}
