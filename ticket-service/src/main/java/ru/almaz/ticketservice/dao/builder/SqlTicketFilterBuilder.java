package ru.almaz.ticketservice.dao.builder;

public interface SqlTicketFilterBuilder<T> {
    String buildSql(T filter);
}
