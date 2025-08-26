package ru.almaz.ticketservice.dto.ticket;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import ru.almaz.ticketservice.annotation.ColumnMapping;
import ru.almaz.ticketservice.annotation.EnumValid;
import ru.almaz.ticketservice.annotation.NotBlankIfPresent;
import ru.almaz.ticketservice.enums.TicketStatus;
import ru.almaz.ticketservice.util.UppercaseDeserializer;

import java.math.BigDecimal;
import java.sql.Timestamp;

public record UpdateTicketRequest(

        @ColumnMapping("route_id")
        @JsonProperty("route_id")
        @Positive(message = "{ticket.route.id.positive}")
        @Schema(description = "Идентификатор маршрута", examples = "1")
        Long routeId,

        @JsonProperty("departure_time")
        @JsonFormat(pattern = "dd.MM.yyyy H:mm")
        @ColumnMapping("departure_time")
        @Schema(description = "Время отправления", examples = "27.08.2025 10:30")
        Timestamp departureTime,

        @JsonProperty("seat_number")
        @ColumnMapping("seat_number")
        @NotBlankIfPresent(message = "{ticket.seat.number.blank}")
        @Size(max = 255, message = "{ticket.seat.number.size}")
        @Schema(description = "Номер места", examples = "10B", maxLength = 255)
        String seatNumber,

        @Positive(message = "{ticket.price.positive}")
        @Schema(description = "Цена билета", examples = "1560")
        BigDecimal price,

        @JsonDeserialize(using = UppercaseDeserializer.class)
        @NotBlankIfPresent(message = "{ticket.status.blank}")
        @EnumValid(enumClass = TicketStatus.class, message = "{ticket.status.invalid}")
        @Size(max = 50, message = "{ticket.status.size}")
        @Schema(description = "Статус билета", examples = "EXPIRED")
        String status
) {
}
