package ru.almaz.ticketservice.dao.builder;

import org.springframework.stereotype.Component;
import ru.almaz.ticketservice.dto.carrier.UpdateCarrierDto;

@Component
public class UpdateCarrierSqlParamsBuilder extends AbstractUpdateSqlParamsBuilder<UpdateCarrierDto> {

    public UpdateCarrierSqlParamsBuilder() {
        super(UpdateCarrierDto.class);
    }

    @Override
    protected String getTableName() {
        return "carrier";
    }
}
