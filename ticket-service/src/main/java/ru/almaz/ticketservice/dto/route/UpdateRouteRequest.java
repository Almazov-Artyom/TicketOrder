package ru.almaz.ticketservice.dto.route;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Positive;
import ru.almaz.ticketservice.annotation.ColumnMapping;
import ru.almaz.ticketservice.annotation.NotBlankIfPresent;

import java.time.Duration;

public record UpdateRouteRequest(
        @NotBlankIfPresent(message = "Пункт отправление не может быть пустым")
        String origin,

        @NotBlankIfPresent(message = "Пункт назначения не может быть пустым")
        String destination,

        @ColumnMapping("carrier_id")
        @JsonProperty("carrier_id")
        @Positive(message = "carrier_id должна быть положительной")
        Long carrierId,

        @Positive(message = "Длительность должна быть положительной")
        Integer duration
) {
}
