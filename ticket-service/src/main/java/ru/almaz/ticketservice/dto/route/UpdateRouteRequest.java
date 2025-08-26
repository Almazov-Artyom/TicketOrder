package ru.almaz.ticketservice.dto.route;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import ru.almaz.ticketservice.annotation.ColumnMapping;
import ru.almaz.ticketservice.annotation.NotBlankIfPresent;


public record UpdateRouteRequest(
        @NotBlankIfPresent(message = "{route.origin.blank}")
        @Size(max = 255, message = "{route.origin.size}")
        String origin,

        @NotBlankIfPresent(message = "{route.destination.blank}")
        @Size(max = 255, message = "{route.destination.size}")
        String destination,

        @ColumnMapping("carrier_id")
        @JsonProperty("carrier_id")
        @Positive(message = "{route.carrier.id.positive}")
        Long carrierId,

        @Positive(message = "{route.duration.positive}")
        Integer duration
) {
}
