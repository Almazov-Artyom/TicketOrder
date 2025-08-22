package ru.almaz.ticketservice.dto.route;

import com.fasterxml.jackson.annotation.JsonProperty;

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
