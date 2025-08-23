package ru.almaz.ticketservice.dto.carrier;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.almaz.ticketservice.annotation.ColumnMapping;
import ru.almaz.ticketservice.annotation.NotBlankIfPresent;

public record UpdateCarrierDto (
        @NotBlankIfPresent(message = "Название перевозчика не может быть пустым")
        String name,

        @JsonProperty("phone_number")
        @ColumnMapping("phone_number")
        @NotBlankIfPresent(message = "Номер телефона не может быть пустым")
        String phoneNumber
){
}
