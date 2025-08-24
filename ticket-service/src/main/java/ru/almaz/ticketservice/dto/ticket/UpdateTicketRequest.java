package ru.almaz.ticketservice.dto.ticket;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Positive;
import ru.almaz.ticketservice.annotation.ColumnMapping;
import ru.almaz.ticketservice.annotation.EnumValid;
import ru.almaz.ticketservice.annotation.NotBlankIfPresent;
import ru.almaz.ticketservice.enums.TicketStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record UpdateTicketRequest(

        @ColumnMapping("route_id")
        @JsonProperty("route_id")
        @Positive(message = "route_id должен быть положительный")
        Long routeId,

        @JsonProperty("departure_time")
        @JsonFormat(pattern = "dd.MM.yyyy H:mm")
        @ColumnMapping("departure_time")
        LocalDateTime departureTime,

        @JsonProperty("seat_number")
        @ColumnMapping("seat_number")
        @NotBlankIfPresent(message = "Номер места не может быть пустым")
        String seatNumber,

        @Positive(message = "Цена должна быть положительной")
        BigDecimal price,

        @NotBlankIfPresent(message = "Статус билета не может быть пустым")
        @EnumValid(enumClass = TicketStatus.class, message = "Некорректный статус билета")
        String status
) {
}
