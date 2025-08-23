package ru.almaz.ticketservice.dto.ticket;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AddTicketRequest(
        @NotNull(message = "route_id не может быть null")
        @JsonProperty("route_id")
        @Positive(message = "route_id должен быть положительным")
        Long routeId,

        @NotNull(message = "departure_time не может быть null")
        @JsonProperty("departure_time")
        LocalDateTime departureTime,

        @NotBlank(message = "Номер места не может быть пустым")
        @JsonProperty("seat_number")
        String seatNumber,

        @NotNull(message = "Цена не можеть быть null")
        @Positive(message = "Цена должна быть положительной")
        BigDecimal price
) {
}
