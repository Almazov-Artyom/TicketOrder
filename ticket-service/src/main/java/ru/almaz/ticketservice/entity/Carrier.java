package ru.almaz.ticketservice.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Carrier {
    private Long id;

    private String name;

    private String phoneNumber;
}
