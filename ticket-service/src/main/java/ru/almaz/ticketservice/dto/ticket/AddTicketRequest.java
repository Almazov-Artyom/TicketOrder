package ru.almaz.ticketservice.dto.ticket;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.sql.Timestamp;

public record AddTicketRequest(
        @NotNull(message = "{ticket.route.id.null}")
        @JsonProperty("route_id")
        @Positive(message = "{ticket.route.id.positive}")
        Long routeId,

        @NotNull(message = "{ticket.departure.time.null}")
        @JsonProperty("departure_time")
        @JsonFormat(pattern = "dd.MM.yyyy H:mm")
        @Future(message = "{ticket.departure.time.future}")
        Timestamp departureTime,

        @NotBlank(message = "{ticket.seat.number.blank}")
        @JsonProperty("seat_number")
        @Size(max = 255, message = "{ticket.seat.number.size}")
        String seatNumber,

        @NotNull(message = "{ticket.price.null}")
        @Positive(message = "{ticket.price.positive}")
        BigDecimal price
) {
}
