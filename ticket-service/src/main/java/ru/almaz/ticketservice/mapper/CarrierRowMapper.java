package ru.almaz.ticketservice.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.almaz.ticketservice.entity.Carrier;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CarrierRowMapper implements RowMapper<Carrier> {
    @Override
    public Carrier mapRow(ResultSet rs, int rowNum) throws SQLException {
        Carrier carrier = new Carrier();

        carrier.setId(rs.getLong("id"));
        carrier.setName(rs.getString("name"));
        carrier.setPhoneNumber(rs.getString("phone_number"));

        return carrier;
    }
}
