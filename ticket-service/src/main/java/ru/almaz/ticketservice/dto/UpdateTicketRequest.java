package ru.almaz.ticketservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ru.almaz.ticketservice.annotation.ColumnMapping;
import ru.almaz.ticketservice.enums.TicketStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record UpdateTicketRequest(

        @ColumnMapping("route_id")
        @JsonProperty("route_id")
        Long routeId,

        @JsonProperty("departure_time")
        @ColumnMapping("departure_time")
        LocalDateTime departureTime,

        @JsonProperty("seat_number")
        @ColumnMapping("seat_number")
        String seatNumber,

        BigDecimal price,

        TicketStatus status
) {
}
