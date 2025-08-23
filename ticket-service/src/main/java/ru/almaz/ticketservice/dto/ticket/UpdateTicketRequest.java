package ru.almaz.ticketservice.dto.ticket;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.almaz.ticketservice.annotation.ColumnMapping;
import ru.almaz.ticketservice.annotation.NotBlankIfPresent;

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
        @NotBlankIfPresent(message = "Номер места не может быть пустым")
        String seatNumber,

        BigDecimal price,

        @NotBlankIfPresent(message = "Статус билета не может быть пустым")
        String status
) {
}
