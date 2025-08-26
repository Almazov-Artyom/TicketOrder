package ru.almaz.ticketservice.dto.ticket;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.sql.Timestamp;

public record AddTicketRequest(
        @NotNull(message = "{ticket.route.id.null}")
        @JsonProperty("route_id")
        @Positive(message = "{ticket.route.id.positive}")
        @Schema(description = "Идентификатор маршрута",examples = "1")
        Long routeId,

        @NotNull(message = "{ticket.departure.time.null}")
        @JsonProperty("departure_time")
        @JsonFormat(pattern = "dd.MM.yyyy H:mm")
        @Future(message = "{ticket.departure.time.future}")
        @Schema(description = "Время отправления", examples = "27.08.2025 10:30")
        Timestamp departureTime,

        @NotBlank(message = "{ticket.seat.number.blank}")
        @JsonProperty("seat_number")
        @Size(max = 255, message = "{ticket.seat.number.size}")
        @Schema(description = "Номер места", examples = "10B", maxLength = 255)
        String seatNumber,

        @NotNull(message = "{ticket.price.null}")
        @Positive(message = "{ticket.price.positive}")
        @Schema(description = "Цена билета", examples = "1560")
        BigDecimal price
) {
}
