package ru.almaz.ticketservice.dao.builder;

import org.springframework.stereotype.Component;
import ru.almaz.ticketservice.dto.ticket.UpdateTicketRequest;

@Component
public class UpdateTicketSqlParamsBuilder
        extends AbstractUpdateSqlParamsBuilder<UpdateTicketRequest>{

    public UpdateTicketSqlParamsBuilder() {
        super(UpdateTicketRequest.class);

    }

    @Override
    protected String getTableName() {
        return "ticket";
    }
}
