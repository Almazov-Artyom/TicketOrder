package ru.almaz.ticketservice.dto.ticket;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.sql.Timestamp;

public record TicketInfo(
        Long id,

        String origin,

        String destination,

        @JsonProperty("carrier_name")
        String carrierName,

        String duration,

        @JsonProperty("departure_time")
        @JsonFormat(pattern = "dd.MM.yyyy H:mm")
        Timestamp departureTime,

        @JsonProperty("seat_number")
        String seatNumber,

        BigDecimal price
) {
}
