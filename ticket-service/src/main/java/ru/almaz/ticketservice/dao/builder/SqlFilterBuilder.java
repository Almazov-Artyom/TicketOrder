package ru.almaz.ticketservice.dao.builder;

public interface SqlFilterBuilder<T> {
    String buildSql(T filter);
}
