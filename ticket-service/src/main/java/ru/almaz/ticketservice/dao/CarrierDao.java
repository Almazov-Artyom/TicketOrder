package ru.almaz.ticketservice.dao;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.almaz.ticketservice.entity.Carrier;

import java.sql.PreparedStatement;
import java.util.Objects;


@Repository
@RequiredArgsConstructor
public class CarrierDao {
    private final JdbcTemplate jdbcTemplate;

    private static final String CREATE_TABLE_SQL = """
                CREATE TABLE IF NOT EXISTS carrier (
                    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY NOT NULL,
                    name VARCHAR(255) NOT NULL,
                    phone_number VARCHAR(255) NOT NULL
                )
            """;

    private static final String SAVE_SQL= """
                INSERT INTO carrier(name, phone_number)
                VALUES (?, ?)
                RETURNING id
            """;

    @PostConstruct
    public void init(){
        jdbcTemplate.execute(CREATE_TABLE_SQL);
    }

    public Carrier save(Carrier carrier) {
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(SAVE_SQL, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, carrier.getName());
            ps.setString(2, carrier.getPhoneNumber());
            return ps;
        }, generatedKeyHolder);

        carrier.setId((Long) generatedKeyHolder.getKey());

        return carrier;
    }
}
