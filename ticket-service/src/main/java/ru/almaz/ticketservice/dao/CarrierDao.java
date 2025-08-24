package ru.almaz.ticketservice.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.almaz.ticketservice.dao.builder.SqlParamsBuilder;
import ru.almaz.ticketservice.dto.carrier.UpdateCarrierDto;
import ru.almaz.ticketservice.entity.Carrier;
import ru.almaz.ticketservice.mapper.row.CarrierRowMapper;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;


@Repository
@RequiredArgsConstructor
public class CarrierDao {
    private final JdbcTemplate jdbcTemplate;

    private final SqlParamsBuilder<UpdateCarrierDto> sqlParamsBuilder;

    private final CarrierRowMapper carrierRowMapper;

    private static final String SAVE_SQL = """
                INSERT INTO carrier(name, phone_number)
                VALUES (?, ?)
                RETURNING id
            """;

    private static final String EXIST_CARRIER_SQL= """
                SELECT EXISTS(
                    SELECT *
                    FROM carrier
                    WHERE id = ?
                )
            """;

    private static final String DELETE_SQL= """
                DELETE FROM carrier
                WHERE id = ?
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

    public Carrier updateCarrier(Long carrierId, UpdateCarrierDto updateCarrierDto) {
        Map.Entry<String, List<Object>> sqlAndParams = sqlParamsBuilder.buildSqlAndParams(updateCarrierDto);
        String sql = sqlAndParams.getKey() + " WHERE id = ?" + " RETURNING id, name, phone_number";
        List<Object> params = sqlAndParams.getValue();
        params.add(carrierId);

        return jdbcTemplate.queryForObject(sql, carrierRowMapper, params.toArray());
    }

    public boolean existCarrier(Long carrierId) {
        Boolean exists = jdbcTemplate.queryForObject(EXIST_CARRIER_SQL, boolean.class, carrierId);
        return Boolean.TRUE.equals(exists);
    }

    public void deleteCarrier(Long carrierId) {
        jdbcTemplate.update(DELETE_SQL, carrierId);
    }

}
