package ru.almaz.savedservice.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.almaz.savedservice.entity.Carrier;

import java.sql.PreparedStatement;

@Repository
@RequiredArgsConstructor
public class CarrierDao {
    private final JdbcTemplate jdbcTemplate;

    private static final String SAVE_SQL = """
                INSERT INTO carrier(name, phone_number)
                VALUES (?, ?)
                RETURNING id
            """;

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
