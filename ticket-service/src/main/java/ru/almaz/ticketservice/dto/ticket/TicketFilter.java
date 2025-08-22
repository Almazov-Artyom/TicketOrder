package ru.almaz.ticketservice.dto.ticket;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import ru.almaz.ticketservice.annotation.ColumnMapping;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class TicketFilter{
        @NotNull(message = "limit не может быть пустым")
        private Integer limit;

        @NotNull(message = "offset не может быть пустым")
        private Integer offset;

        @JsonProperty("departure_date")
        @JsonFormat(pattern = "dd.MM.yyyy")
        private LocalDate departureDate;

        @JsonProperty("departure_time")
        private LocalTime departureTime;

        @ColumnMapping("t.departure_time")
        private Timestamp departureDateTime;

        @ColumnMapping("r.origin")
        private String origin;

        @ColumnMapping("r.destination")
        private String destination;

        @ColumnMapping("c.name")
        @JsonProperty("carrier_name")
        private String carrierName;
}


