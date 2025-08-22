package ru.almaz.ticketservice.dao;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.almaz.ticketservice.dao.builder.SqlParamsBuilder;
import ru.almaz.ticketservice.dao.builder.UpdateRouteSqlParamsBuilder;
import ru.almaz.ticketservice.dto.UpdateCarrierDto;
import ru.almaz.ticketservice.dto.UpdateRouteRequest;
import ru.almaz.ticketservice.entity.Carrier;
import ru.almaz.ticketservice.entity.Route;
import ru.almaz.ticketservice.mapper.RouteRowMapper;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class RouteDao {
    private final JdbcTemplate jdbcTemplate;

    private final SqlParamsBuilder<UpdateRouteRequest> sqlParamsBuilder;

    private final RouteRowMapper routeRowMapper;

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

    private static final String EXIST_ROUTE_SQL= """
                SELECT EXISTS(
                    SELECT *
                    FROM route
                    WHERE id = ?
                )
            """;

    @PostConstruct
    public void init() {
        jdbcTemplate.execute(CREATE_TABLE_SQL);
    }

    public Route save(Route route) {
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(SAVE_SQL, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, route.getOrigin());
            ps.setString(2, route.getDestination());
            ps.setLong(3, route.getCarrier().getId());
            ps.setInt(4, (int) route.getDuration().toMinutes());
            return ps;
        }, generatedKeyHolder);

        route.setId((Long) generatedKeyHolder.getKey());

        return route;
    }

    public Route updateRoute(Long routeId, UpdateRouteRequest updateRouteRequest) {
        Map.Entry<String, List<Object>> sqlAndParams = sqlParamsBuilder.buildSqlAndParams(updateRouteRequest);
        String sql = sqlAndParams.getKey() + " WHERE id = ? RETURNING id, origin, destination, carrier_id, duration";
        List<Object> params = sqlAndParams.getValue();
        params.add(routeId);

        return jdbcTemplate.queryForObject(sql, routeRowMapper, params.toArray());
    }

    public boolean existRoute(Long routeId) {
        Boolean exists = jdbcTemplate.queryForObject(EXIST_ROUTE_SQL, Boolean.class, routeId);
        return Boolean.TRUE.equals(exists);
    }
}
