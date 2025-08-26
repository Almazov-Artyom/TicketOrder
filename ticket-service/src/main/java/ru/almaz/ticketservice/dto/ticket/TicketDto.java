package ru.almaz.ticketservice.dto.ticket;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import ru.almaz.ticketservice.enums.TicketStatus;

import java.math.BigDecimal;
import java.sql.Timestamp;

public record TicketDto(
        @Schema(description = "Идентификатор билета",examples = "1")
        Long id,

        @JsonProperty("route_id")
        @Schema(description = "Идентификатор маршрута",examples = "1")
        Long routeId,

        @JsonProperty("departure_time")
        @JsonFormat(pattern = "dd.MM.yyyy H:mm")
        @Schema(description = "Время отправления", examples = "27.08.2025 10:30")
        Timestamp departureTime,

        @JsonProperty("seat_number")
        @Schema(description = "Номер места",examples = "10B", maxLength = 255)
        String seatNumber,

        @Schema(description = "Цена билета", examples = "1560")
        BigDecimal price,

        @Schema(description = "Статус билета", examples = "EXPIRED")
        TicketStatus status
) {
}
