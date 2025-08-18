package ru.almaz.ticketservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import ru.almaz.ticketservice.annotation.ColumnMapping;

import java.sql.Timestamp;

@Getter
@Setter
public class TicketFilter{
        @NotNull(message = "limit не может быть пустым")
        private Integer limit;

        @NotNull(message = "offset не может быть пустым")
        private Integer offset;

        @ColumnMapping("t.departure_time")
        private Timestamp departureTime;

        @ColumnMapping("r.origin")
        private String origin;

        @ColumnMapping("r.destination")
        private String destination;

        @ColumnMapping("c.name")
        private String carrierName;
}


