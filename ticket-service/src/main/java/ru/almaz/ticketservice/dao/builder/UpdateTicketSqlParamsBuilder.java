package ru.almaz.ticketservice.dao.builder;

import org.springframework.stereotype.Component;

@Component
public class UpdateTicketSqlParamsBuilder
        extends AbstractUpdateSqlParamsBuilder<UpdateTicketSqlParamsBuilder>{

    public UpdateTicketSqlParamsBuilder() {
        super(UpdateTicketSqlParamsBuilder.class);

    }

    @Override
    protected String getTableName() {
        return "ticket";
    }
}
