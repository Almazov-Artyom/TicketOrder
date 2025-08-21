package ru.almaz.ticketservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.Duration;

public record RouteDto(

        Long id,

        String origin,

        String destination,

        @JsonProperty("carrier_id")
        Long carrierId,

        Duration duration
) {
}
