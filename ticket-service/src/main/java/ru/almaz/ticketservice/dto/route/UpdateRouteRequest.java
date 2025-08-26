package ru.almaz.ticketservice.dto.route;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import ru.almaz.ticketservice.annotation.ColumnMapping;
import ru.almaz.ticketservice.annotation.NotBlankIfPresent;


public record UpdateRouteRequest(
        @NotBlankIfPresent(message = "{route.origin.blank}")
        @Size(max = 255, message = "{route.origin.size}")
        @Schema(description = "Пункт отправления", examples = "Москва", maxLength = 255)
        String origin,

        @NotBlankIfPresent(message = "{route.destination.blank}")
        @Size(max = 255, message = "{route.destination.size}")
        @Schema(description = "Пункт назначения", examples = "Нижний Новгород", maxLength = 255)
        String destination,

        @ColumnMapping("carrier_id")
        @JsonProperty("carrier_id")
        @Positive(message = "{route.carrier.id.positive}")
        @Schema(description = "Идентификатор перевозчика", examples = "1")
        Long carrierId,

        @Positive(message = "{route.duration.positive}")
        @Schema(description = "Длительность поездки в минутах", examples = "90")
        Integer duration
) {
}
