package ru.almaz.ticketservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.almaz.ticketservice.dto.carrier.CarrierDto;
import ru.almaz.ticketservice.dto.carrier.AddCarrierRequest;
import ru.almaz.ticketservice.entity.Carrier;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CarrierMapper {
    Carrier toCarrier(AddCarrierRequest carrierRequest);

    CarrierDto toCarrierDto(Carrier carrier);

}
