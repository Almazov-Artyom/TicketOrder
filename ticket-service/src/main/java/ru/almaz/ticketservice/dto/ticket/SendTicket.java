package ru.almaz.ticketservice.dto.ticket;

import java.math.BigDecimal;
import java.sql.Timestamp;

public record SendTicket(
        Long ticketId,

        String origin,

        String destination,

        String carrierName,

        Integer duration,

        Timestamp departureTime,

        String seatNumber,

        BigDecimal price
) {
}
