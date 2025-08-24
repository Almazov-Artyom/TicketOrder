package ru.almaz.ticketservice.dto.route;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.Duration;

public record AddRouteRequest(
        @NotBlank(message = "Пункт отправления не может быть пустым")
        String origin,

        @NotBlank(message = "Пункт назначения не может быть пустым")
        String destination,

        @NotNull(message = "carrier_id не может быть null")
        @Positive(message = "carrier_id должна быть положительной")
        @JsonProperty("carrier_id")
        Long carrierId,

        @NotNull(message = "Длительность не может быть null")
        @Positive(message = "Длительность должна быть положительной")
        Integer duration
) {
}
