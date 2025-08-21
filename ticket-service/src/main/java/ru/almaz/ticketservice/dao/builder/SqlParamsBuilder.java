package ru.almaz.ticketservice.dao.builder;

import java.util.List;
import java.util.Map;

public interface SqlParamsBuilder<T> {
    Map.Entry<String, List<Object>> buildSqlAndParams(T t);
}
