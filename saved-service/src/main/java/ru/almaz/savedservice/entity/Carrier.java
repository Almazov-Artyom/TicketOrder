package ru.almaz.savedservice.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Carrier {
    private Long id;

    private String name;

    private String phoneNumber;
}
