package ru.almaz.ticketservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.Duration;

public record RouteRequest (
        @NotBlank(message = "Пункт отправления не может быть пустым")
        String origin,

        @NotBlank(message = "Пункт назначения не может быть пустым")
        String destination,

        @NotNull(message = "carrier_id не может быть null")
        @JsonProperty("carrier_id")
        Long carrierId,

        @NotNull(message = "Длительность не может быть null")
        Duration duration
) {
}
