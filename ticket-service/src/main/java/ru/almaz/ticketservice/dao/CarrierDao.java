package ru.almaz.ticketservice.dao;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CarrierDao {
    private final JdbcTemplate jdbcTemplate;

    private static final String CREATE_TABLE_SQL = """
                CREATE TABLE carrier (
                    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY NOT NULL,
                    name VARCHAR(255) NOT NULL,
                    phone_number VARCHAR(255) NOT NULL
                )
            """;

    @PostConstruct
    public void init(){
        jdbcTemplate.execute(CREATE_TABLE_SQL);
    }
}
