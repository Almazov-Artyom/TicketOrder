package ru.almaz.ticketservice.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.Duration;

@Getter
@Setter
public class Route {
    private Long id;

    private String origin;

    private String destination;

    private Carrier carrier;

    private Duration duration;
}
