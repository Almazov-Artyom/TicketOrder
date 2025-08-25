package ru.almaz.savedservice.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.almaz.savedservice.entity.Route;

import java.sql.PreparedStatement;


@Repository
@RequiredArgsConstructor
public class RouteDao {
    private final JdbcTemplate jdbcTemplate;

    private static final String SAVE_SQL = """
            INSERT INTO route (origin, destination, carrier_id, duration)
            VALUES (?, ?, ?, ?)
            RETURNING id;
            """;

    public Route save(Route route) {
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(SAVE_SQL, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, route.getOrigin());
            ps.setString(2, route.getDestination());
            ps.setLong(3, route.getCarrier().getId());
            ps.setInt(4, route.getDuration());
            return ps;
        }, generatedKeyHolder);

        route.setId((Long) generatedKeyHolder.getKey());

        return route;
    }
}
