package ru.almaz.ticketservice.dto.route;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;


public record AddRouteRequest(
        @NotBlank(message = "Пункт отправления не может быть пустым")
        @Size(max = 255, message = "Пункт отправления не больше 255 символов")
        String origin,

        @NotBlank(message = "Пункт назначения не может быть пустым")
        @Size(max = 255, message = "Пункт назначения не больше 255 символов")
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
