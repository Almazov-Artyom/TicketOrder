package ru.almaz.ticketservice.dto.ticket;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;
import ru.almaz.ticketservice.annotation.ColumnMapping;
import ru.almaz.ticketservice.annotation.NotBlankIfPresent;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class TicketFilter{
        @NotNull(message = "limit не может быть пустым")
        @Positive(message = "limit должен быть положительным")
        private Integer limit;

        @NotNull(message = "offset не может быть пустым")
        @PositiveOrZero(message = "offset должен быть неотрицательный")
        private Integer offset;

        @JsonProperty("departure_date")
        @JsonFormat(pattern = "dd.MM.yyyy")
        private LocalDate departureDate;

        @JsonProperty("departure_time")
        private LocalTime departureTime;

        @ColumnMapping("t.departure_time")
        private Timestamp departureDateTime;

        @ColumnMapping("r.origin")
        @NotBlankIfPresent(message = "Пункт отправления не может быть пустым")
        private String origin;

        @ColumnMapping("r.destination")
        @NotBlankIfPresent(message = "Пункт назначения не может быть пустым")
        private String destination;

        @ColumnMapping("c.name")
        @JsonProperty("carrier_name")
        @NotBlankIfPresent(message = "Название перевозчика не может быть пустым")
        private String carrierName;
}


