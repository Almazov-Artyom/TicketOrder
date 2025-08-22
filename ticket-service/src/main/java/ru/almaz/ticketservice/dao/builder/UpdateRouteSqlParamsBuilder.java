package ru.almaz.ticketservice.dao.builder;

import org.springframework.stereotype.Component;
import ru.almaz.ticketservice.dto.route.UpdateRouteRequest;

@Component
public class UpdateRouteSqlParamsBuilder
        extends AbstractUpdateSqlParamsBuilder<UpdateRouteRequest> {

    public UpdateRouteSqlParamsBuilder() {
        super(UpdateRouteRequest.class);
    }

    @Override
    protected String getTableName() {
        return "route";
    }
}
