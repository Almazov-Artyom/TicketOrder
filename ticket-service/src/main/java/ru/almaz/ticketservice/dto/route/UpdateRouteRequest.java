package ru.almaz.ticketservice.dto.route;

import com.fasterxml.jackson.annotation.JsonProperty;
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
