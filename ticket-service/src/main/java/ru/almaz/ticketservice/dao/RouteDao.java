package ru.almaz.ticketservice.dao;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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

    @PostConstruct
    public void init() {
        jdbcTemplate.execute(CREATE_TABLE_SQL);
    }
}
