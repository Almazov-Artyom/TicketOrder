package ru.almaz.ticketservice.dto.carrier;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CarrierDto(
        Long id,

        String name,

        @JsonProperty("phone_number")
        String phoneNumber
) {
}
