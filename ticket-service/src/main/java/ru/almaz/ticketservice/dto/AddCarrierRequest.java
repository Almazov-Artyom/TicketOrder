package ru.almaz.ticketservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record AddCarrierRequest(
        @NotBlank(message = "Имя не может быть пустым")
        String name,

        @NotBlank(message = "Номер телефона не может быть пустым")
        @JsonProperty("phone_number")
        String phoneNumber

){
}
