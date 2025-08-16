package ru.almaz.ticketservice.dao;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TicketDao {
    private final JdbcTemplate jdbcTemplate;

    private static final String CREATE_TABLE_SQL= """
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

    @PostConstruct
    public void init() {
        jdbcTemplate.execute(CREATE_TABLE_SQL);
    }
}
