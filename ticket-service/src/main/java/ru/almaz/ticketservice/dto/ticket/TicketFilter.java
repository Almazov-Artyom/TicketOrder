package ru.almaz.ticketservice.dto.ticket;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
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
        @NotNull(message = "{ticket.limit.null}")
        @Positive(message = "{ticket.limit.positive}")
        @Schema(description = "Количество билетов, которые нужно вернуть", examples = "10")
        private Integer limit;

        @NotNull(message = "{ticket.offset.null}")
        @PositiveOrZero(message = "{ticket.offset.positive}")
        @Schema(description = "Смещение от начала выборки", example = "0")
        private Integer offset;

        @JsonProperty("departure_date")
        @JsonFormat(pattern = "dd.MM.yyyy")
        @Schema(description = "Дата отправления", examples = "27.08.2025")
        private LocalDate departureDate;

        @JsonProperty("departure_time")
        @JsonFormat(pattern = "H:mm")
        @Schema(description = "Время отправления. Если дата не указана, то в качестве даты возьмется сегодняшняя", examples = "10:30")
        private LocalTime departureTime;

        @ColumnMapping("t.departure_time")
        private Timestamp departureDateTime;

        @ColumnMapping("r.origin")
        @NotBlankIfPresent(message = "{route.origin.blank}")
        @Size(max = 255, message = "{route.origin.size}")
        @Schema(description = "Пункт отправления", examples = "Москва", maxLength = 255)
        private String origin;

        @ColumnMapping("r.destination")
        @NotBlankIfPresent(message = "{route.destination.blank}")
        @Size(max = 255, message = "{route.destination.size}")
        @Schema(description = "Пункт назначения", examples = "Нижний Новгород", maxLength = 255)
        private String destination;

        @ColumnMapping("c.name")
        @JsonProperty("carrier_name")
        @NotBlankIfPresent(message = "{carrier.name.blank}")
        @Size(max = 255, message = "{carrier.name.size}")
        @Schema(description = "Название перевозчика", examples = "РЖД", maxLength = 255)
        private String carrierName;
}
