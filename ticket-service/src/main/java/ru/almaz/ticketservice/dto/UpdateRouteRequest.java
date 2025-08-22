package ru.almaz.ticketservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ru.almaz.ticketservice.annotation.ColumnMapping;

import java.time.Duration;

public record UpdateRouteRequest(
        String origin,

        String destination,

        @ColumnMapping("carrier_id")
        @JsonProperty("carrier_id")
        Long carrierId,

        Duration duration
) {
}
