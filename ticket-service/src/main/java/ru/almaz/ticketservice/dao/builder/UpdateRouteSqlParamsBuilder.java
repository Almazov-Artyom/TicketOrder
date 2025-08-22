package ru.almaz.ticketservice.dao.builder;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import ru.almaz.ticketservice.annotation.ColumnMapping;
import ru.almaz.ticketservice.dto.UpdateCarrierDto;
import ru.almaz.ticketservice.dto.UpdateRouteRequest;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
