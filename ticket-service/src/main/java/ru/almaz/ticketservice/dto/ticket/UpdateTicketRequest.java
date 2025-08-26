package ru.almaz.ticketservice.dto.ticket;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
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
        Long routeId,

        @JsonProperty("departure_time")
        @JsonFormat(pattern = "dd.MM.yyyy H:mm")
        @ColumnMapping("departure_time")
        Timestamp departureTime,

        @JsonProperty("seat_number")
        @ColumnMapping("seat_number")
        @NotBlankIfPresent(message = "{ticket.seat.number.blank}")
        @Size(max = 255, message = "{ticket.seat.number.size}")
        String seatNumber,

        @Positive(message = "{ticket.price.positive}")
        BigDecimal price,

        @JsonDeserialize(using = UppercaseDeserializer.class)
        @NotBlankIfPresent(message = "{ticket.status.blank}")
        @EnumValid(enumClass = TicketStatus.class, message = "{ticket.status.invalid}")
        @Size(max = 50, message = "{ticket.status.size}")
        String status
) {
}
