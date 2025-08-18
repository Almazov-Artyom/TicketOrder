package ru.almaz.ticketservice.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public record TicketDto(
        String origin,
        String destination,
        String carrierName,
        String duration,
        LocalDateTime departureTime,
        String seatNumber,
        BigDecimal price
) {
}
