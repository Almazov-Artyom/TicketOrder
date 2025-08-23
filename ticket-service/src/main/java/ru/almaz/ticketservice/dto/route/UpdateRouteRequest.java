package ru.almaz.ticketservice.dto.route;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.almaz.ticketservice.annotation.ColumnMapping;
import ru.almaz.ticketservice.annotation.NotBlankIfPresent;

import java.time.Duration;

public record UpdateRouteRequest(
        @NotBlankIfPresent(,message = "Пункт отправление не может быть пустым")
        String origin,

        @NotBlankIfPresent(message = "Пункт назначения не может быть пустым")
        String destination,

        @ColumnMapping("carrier_id")
        @JsonProperty("carrier_id")
        Long carrierId,

        Duration duration
) {
}
