package ru.almaz.ticketservice.dto.route;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;


public record AddRouteRequest(
        @NotBlank(message = "{route.origin.blank}")
        @Size(max = 255, message = "{route.origin.size}")
        @Schema(description = "Пункт отправления", examples = "Москва", maxLength = 255)
        String origin,

        @NotBlank(message = "{route.destination.blank}")
        @Size(max = 255, message = "{route.destination.size}")
        @Schema(description = "Пункт назначения", examples = "Нижний Новгород", maxLength = 255)
        String destination,

        @NotNull(message = "{route.carrier.id.null}")
        @Positive(message = "{route.carrier.id.positive}")
        @JsonProperty("carrier_id")
        @Schema(description = "Идентификатор перевозчика", examples = "1")
        Long carrierId,

        @NotNull(message = "{route.duration.null}")
        @Positive(message = "{route.duration.positive}")
        @Schema(description = "Длительность поездки в минутах", examples = "90")
        Integer duration
) {
}
