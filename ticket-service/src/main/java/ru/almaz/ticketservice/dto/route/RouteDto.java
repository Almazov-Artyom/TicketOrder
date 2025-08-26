package ru.almaz.ticketservice.dto.route;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

public record RouteDto(
        @Schema(description = "Идентификатор маршрута",examples = "1")
        Long id,

        @Schema(description = "Пункт отправления", examples = "Москва")
        String origin,

        @Schema(description = "Пункт назначения", examples = "Нижний Новгород", maxLength = 255)
        String destination,

        @JsonProperty("carrier_id")
        @Schema(description = "Идентификатор перевозчика",examples = "1")
        Long carrierId,

        @Schema(description = "Длительность поездки в минутах", examples = "90")
        Integer duration
) {
}
