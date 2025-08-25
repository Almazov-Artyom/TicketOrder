package ru.almaz.ticketservice.dto.route;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import ru.almaz.ticketservice.annotation.ColumnMapping;
import ru.almaz.ticketservice.annotation.NotBlankIfPresent;


public record UpdateRouteRequest(
        @NotBlankIfPresent(message = "Пункт отправление не может быть пустым")
        @Size(max = 255, message = "Пункт отправления не больше 255 символов")
        String origin,

        @NotBlankIfPresent(message = "Пункт назначения не может быть пустым")
        @Size(max = 255, message = "Пункт назначения не больше 255 символов")
        String destination,

        @ColumnMapping("carrier_id")
        @JsonProperty("carrier_id")
        @Positive(message = "carrier_id должна быть положительной")
        Long carrierId,

        @Positive(message = "Длительность должна быть положительной")
        Integer duration
) {
}
