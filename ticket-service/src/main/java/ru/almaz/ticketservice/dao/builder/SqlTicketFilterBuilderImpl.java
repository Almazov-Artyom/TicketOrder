package ru.almaz.ticketservice.dao.builder;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import ru.almaz.ticketservice.annotation.ColumnMapping;
import ru.almaz.ticketservice.dto.TicketFilter;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@Component
public class SqlTicketFilterBuilderImpl implements SqlTicketFilterBuilder<TicketFilter> {

    private final Map<String, String> fieldToColumn;

    private static final String FIND_AVAILABLE_TICKETS_SQL = """
                SELECT
                    t.id AS ticket_id,
                    r.origin AS route_origin,
                    r.destination AS route_destination,
                    c.name AS carrier_name,
                    r.duration AS route_duration,
                    t.departure_time AS ticket_departure_time,
                    t.seat_number AS ticker_seat_number,
                    t.price as ticket_price
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

    public SqlTicketFilterBuilderImpl() {
        fieldToColumn = new HashMap<>();
        cacheFields();
    }

    @Override
    @SneakyThrows
    public String buildSql(TicketFilter filter) {
        StringBuilder sql = new StringBuilder(FIND_AVAILABLE_TICKETS_SQL);

        Class<TicketFilter> ticketFilterClass = TicketFilter.class;
        Field[] declaredFields = ticketFilterClass.getDeclaredFields();

        for (Field declaredField : declaredFields) {
            declaredField.setAccessible(true);
            Object value = declaredField.get(filter);
            if (value != null) {
                String column = fieldToColumn.get(declaredField.getName());
                if (column == null) continue;

                sql.append("AND ").append(column);
                if (value instanceof String strValue) {
                    sql.append(" ILIKE '%").append(strValue).append("%' ");
                } else {
                    sql.append(" = '").append(value).append("' ");
                }
            }
        }
        sql.append("AND t.status='AVAILABLE'");
        sql.append(" ORDER BY t.departure_time");
        sql.append(String.format(" LIMIT %s OFFSET %d ",filter.limit(), filter.offset()));
        return sql.toString();
    }
}
