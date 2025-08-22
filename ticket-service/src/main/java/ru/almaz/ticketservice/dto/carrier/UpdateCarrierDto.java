package ru.almaz.ticketservice.dto.carrier;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.almaz.ticketservice.annotation.ColumnMapping;

public record UpdateCarrierDto (
        String name,

        @JsonProperty("phone_number")
        @ColumnMapping("phone_number")
        String phoneNumber
){
}
