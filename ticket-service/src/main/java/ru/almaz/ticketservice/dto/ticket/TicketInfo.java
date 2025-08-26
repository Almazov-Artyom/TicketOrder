package ru.almaz.ticketservice.dto.ticket;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.sql.Timestamp;

public record TicketInfo(
        @Schema(description = "Идентификатор билета", examples = "1")
        Long id,

        @Schema(description = "Пункт отправления", examples = "Москва")
        String origin,

        @Schema(description = "Пункт назначения", examples = "Нижний Новгород")
        String destination,

        @JsonProperty("carrier_name")
        @Schema(description = "Название перевозчика", examples = "РЖД")
        String carrierName,

        @Schema(description = "Длительность поездки в минутах", examples = "90 min")
        String duration,

        @JsonProperty("departure_time")
        @JsonFormat(pattern = "dd.MM.yyyy H:mm")
        @Schema(description = "Время отправления", examples = "27.08.2025 10:30")
        Timestamp departureTime,

        @JsonProperty("seat_number")
        @Schema(description = "Номер места", examples = "10B")
        String seatNumber,

        @Schema(description = "Цена билета", examples = "1560")
        BigDecimal price
) {
}
