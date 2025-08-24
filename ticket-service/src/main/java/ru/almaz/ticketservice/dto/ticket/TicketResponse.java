package ru.almaz.ticketservice.dto.ticket;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.almaz.ticketservice.enums.TicketStatus;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public record TicketResponse(
        Long id,

        @JsonProperty("route_id")
        Long routeId,

        @JsonProperty("departure_time")
        @JsonFormat(pattern = "dd.MM.yyyy H:mm")
        Timestamp departureTime,

        @JsonProperty("seat_number")
        String seatNumber,

        BigDecimal price,

        TicketStatus status
) {
}
