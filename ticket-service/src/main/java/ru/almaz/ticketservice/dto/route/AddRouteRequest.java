package ru.almaz.ticketservice.dto.route;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;


public record AddRouteRequest(
        @NotBlank(message = "{route.origin.blank}")
        @Size(max = 255, message = "{route.origin.size}")
        String origin,

        @NotBlank(message = "{route.destination.blank}")
        @Size(max = 255, message = "{route.destination.size}")
        String destination,

        @NotNull(message = "{route.carrier.id.null}")
        @Positive(message = "{route.carrier.id.positive}")
        @JsonProperty("carrier_id")
        Long carrierId,

        @NotNull(message = "{route.duration.null}")
        @Positive(message = "{route.duration.positive}")
        Integer duration
) {
}
