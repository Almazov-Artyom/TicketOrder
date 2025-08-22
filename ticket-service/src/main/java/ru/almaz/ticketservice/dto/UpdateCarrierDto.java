package ru.almaz.ticketservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import ru.almaz.ticketservice.annotation.ColumnMapping;

public record UpdateCarrierDto (
        String name,

        @JsonProperty("phone_number")
        @ColumnMapping("phone_number")
        String phoneNumber
){
}
