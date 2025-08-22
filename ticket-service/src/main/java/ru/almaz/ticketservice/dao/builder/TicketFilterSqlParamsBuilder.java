package ru.almaz.ticketservice.dao.builder;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import ru.almaz.ticketservice.annotation.ColumnMapping;
import ru.almaz.ticketservice.dto.ticket.TicketFilter;

import java.lang.reflect.Field;
import java.util.*;

@Component
public class TicketFilterSqlParamsBuilder implements SqlParamsBuilder<TicketFilter> {

    private final Map<String, String> fieldToColumn;

    private static final String FIND_AVAILABLE_TICKETS_SQL = """
                SELECT
                    t.id AS ticket_id,
                    r.id AS route_id,
                    r.origin AS route_origin,
                    r.destination AS route_destination,
                    c.id AS carrier_id,
                    c.name AS carrier_name,
                    c.phone_number AS carrier_phone_number,
                    r.duration AS route_duration,
                    t.departure_time AS ticket_departure_time,
                    t.seat_number AS ticket_seat_number,
                    t.price AS ticket_price,
                    t.status AS ticket_status
                FROM ticket AS t
                JOIN route AS r ON t.route_id = r.id
                JOIN carrier AS c ON r.carrier_id = c.id
                WHERE 1=1
            """;


    private void cacheFields() {
        Class<TicketFilter> ticketFilterClass = TicketFilter.class;
        Field[] declaredFields = ticketFilterClass.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            if (declaredField.isAnnotationPresent(ColumnMapping.class)) {
                String value = declaredField.getAnnotation(ColumnMapping.class).value();
                declaredField.setAccessible(true);
                fieldToColumn.put(declaredField.getName(), value);
            }
        }
    }

    public TicketFilterSqlParamsBuilder() {
        fieldToColumn = new HashMap<>();
        cacheFields();
    }

    @Override
    @SneakyThrows
    public Map.Entry<String, List<Object>> buildSqlAndParams(TicketFilter t) {
        StringBuilder sql = new StringBuilder(FIND_AVAILABLE_TICKETS_SQL);
        List<Object> params = new ArrayList<>();

        Class<TicketFilter> ticketFilterClass = TicketFilter.class;
        Field[] declaredFields = ticketFilterClass.getDeclaredFields();

        for (Field declaredField : declaredFields) {
            declaredField.setAccessible(true);
            Object value = declaredField.get(t);
            if (value != null) {
                String column = fieldToColumn.get(declaredField.getName());
                if (column == null) continue;

                sql.append(" AND ").append(column);
                if (value instanceof String) {
                    sql.append(" ILIKE ?");
                    params.add("%" + value + "%");
                } else {
                    sql.append(" >= ? ");
                    params.add(value);
                }
            }
        }

        sql.append(" AND t.status='AVAILABLE'");
        sql.append(" ORDER BY t.departure_time LIMIT ? OFFSET ?");

        params.add(t.getLimit());
        params.add(t.getOffset());

        return Map.entry(sql.toString(), params);
    }
}
