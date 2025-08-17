package ru.almaz.ticketservice.dto;

import jakarta.validation.constraints.NotBlank;
import ru.almaz.ticketservice.annotation.ColumnMapping;

import java.time.LocalDateTime;

public record TicketFilter(
        @NotBlank(message = "limit не может быть пустым")
        Integer limit,

        @NotBlank(message = "offset не может быть пустым")
        Integer offset,

        @ColumnMapping("t.departure_time")
        LocalDateTime departureTime,

        @ColumnMapping("r.origin")
        String origin,

        @ColumnMapping("r.destination")
        String destination,

        @ColumnMapping("c.name")
        String carrierName
) {
}
