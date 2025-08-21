package ru.almaz.ticketservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CarrierDto(
        Long id,

        String name,

        @JsonProperty("phone_number")
        String phoneNumber
) {
}
