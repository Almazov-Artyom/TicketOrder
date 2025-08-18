package ru.almaz.ticketservice.dao.builder;

import java.util.List;
import java.util.Map;

public interface SqlTicketFilterBuilder<T> {
    Map.Entry<String, List<Object>> buildSqlAndParams(T filter);
}
