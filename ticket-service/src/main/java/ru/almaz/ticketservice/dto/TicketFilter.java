package ru.almaz.ticketservice.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record TicketFilter(
        @NotBlank(message = "limit не может быть пустым")
        Integer limit,

        @NotBlank(message = "offset не может быть пустым")
        Integer offset,

        LocalDateTime localDateTime,

        String origin,

        String destination,

        String carrierName
) {
}
