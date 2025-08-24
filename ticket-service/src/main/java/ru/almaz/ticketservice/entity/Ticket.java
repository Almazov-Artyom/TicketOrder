package ru.almaz.ticketservice.entity;

import lombok.Getter;
import lombok.Setter;
import ru.almaz.ticketservice.enums.TicketStatus;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Setter
public class Ticket {
    private Long id;

    private Route route;

    private Timestamp departureTime;

    private String seatNumber;

    private BigDecimal price;

    private TicketStatus status;
}
