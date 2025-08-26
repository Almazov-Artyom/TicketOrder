package ru.almaz.ticketservice.dto.ticket;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
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
        private Integer limit;

        @NotNull(message = "{ticket.offset.null}")
        @PositiveOrZero(message = "{ticket.offset.positive}")
        private Integer offset;

        @JsonProperty("departure_date")
        @JsonFormat(pattern = "dd.MM.yyyy")
        private LocalDate departureDate;

        @JsonProperty("departure_time")
        private LocalTime departureTime;

        @ColumnMapping("t.departure_time")
        private Timestamp departureDateTime;

        @ColumnMapping("r.origin")
        @NotBlankIfPresent(message = "{route.origin.blank}")
        @Size(max = 255, message = "{route.origin.size}")
        private String origin;

        @ColumnMapping("r.destination")
        @NotBlankIfPresent(message = "{route.destination.blank}")
        @Size(max = 255, message = "{route.destination.size}")
        private String destination;

        @ColumnMapping("c.name")
        @JsonProperty("carrier_name")
        @NotBlankIfPresent(message = "{carrier.name.blank}")
        @Size(max = 255, message = "{carrier.name.size}")
        private String carrierName;
}


