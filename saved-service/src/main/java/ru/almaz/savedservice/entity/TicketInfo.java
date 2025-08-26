package ru.almaz.savedservice.entity;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Setter
public class TicketInfo {

    private Long id;

    private Long ticketId;

    private String origin;

    private String destination;

    private String carrierName;

    private Integer duration;

    private Timestamp departureTime;

    private String seatNumber;

    private BigDecimal price;
}
