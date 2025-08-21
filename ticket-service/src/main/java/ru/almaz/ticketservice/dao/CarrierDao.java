package ru.almaz.ticketservice.dao;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.almaz.ticketservice.dao.builder.SqlParamsBuilder;
import ru.almaz.ticketservice.dto.UpdateCarrierDto;
import ru.almaz.ticketservice.entity.Carrier;
import ru.almaz.ticketservice.mapper.CarrierRowMapper;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;


@Repository
@RequiredArgsConstructor
public class CarrierDao {
    private final JdbcTemplate jdbcTemplate;

    private final SqlParamsBuilder<UpdateCarrierDto> sqlParamsBuilder;

    private final CarrierRowMapper carrierRowMapper;

    private static final String CREATE_TABLE_SQL = """
                CREATE TABLE IF NOT EXISTS carrier (
                    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY NOT NULL,
                    name VARCHAR(255) NOT NULL,
                    phone_number VARCHAR(255) NOT NULL
                )
            """;

    private static final String SAVE_SQL = """
                INSERT INTO carrier(name, phone_number)
                VALUES (?, ?)
                RETURNING id
            """;

    @PostConstruct
    public void init() {
        jdbcTemplate.execute(CREATE_TABLE_SQL);
        UpdateCarrierDto transporter = new UpdateCarrierDto("Transporter", "79081562063");
        updateCarrier(7L, transporter);
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

    public Carrier updateCarrier(Long carrierId, UpdateCarrierDto updateCarrierDto) {
        Map.Entry<String, List<Object>> sqlAndParams = sqlParamsBuilder.buildSqlAndParams(updateCarrierDto);
        String sql = sqlAndParams.getKey() + " WHERE id = ?" + " RETURNING id, name, phone_number";
        List<Object> params = sqlAndParams.getValue();
        params.add(carrierId);

        return jdbcTemplate.queryForObject(sql, carrierRowMapper, params.toArray());
    }
}
