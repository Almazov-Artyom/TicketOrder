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
        @Positive(message = "route_id должен быть положительный")
        Long routeId,

        @JsonProperty("departure_time")
        @JsonFormat(pattern = "dd.MM.yyyy H:mm")
        @ColumnMapping("departure_time")
        Timestamp departureTime,

        @JsonProperty("seat_number")
        @ColumnMapping("seat_number")
        @NotBlankIfPresent(message = "Номер места не может быть пустым")
        @Size(max = 255, message = "Номер места не больше 255 символов")
        String seatNumber,

        @Positive(message = "Цена должна быть положительной")
        BigDecimal price,

        @JsonDeserialize(using = UppercaseDeserializer.class)
        @NotBlankIfPresent(message = "Статус билета не может быть пустым")
        @EnumValid(enumClass = TicketStatus.class, message = "Некорректный статус билета")
        @Size(max = 50, message = "Статус билета не больше 50 символов")
        String status
) {
}
