package ru.almaz.ticketservice.entity;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Route {
    private Long id;

    private String origin;

    private String destination;

    private Carrier carrier;

    private Integer duration;
}
