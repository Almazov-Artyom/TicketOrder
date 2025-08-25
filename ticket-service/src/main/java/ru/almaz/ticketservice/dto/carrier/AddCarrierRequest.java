package ru.almaz.ticketservice.dto.carrier;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record AddCarrierRequest(
        @NotBlank(message = "Имя не может быть пустым")
        @Size(max = 255, message = "Название не больше 255 символов")
        String name,

        @NotBlank(message = "Номер телефона не может быть пустым")
        @JsonProperty("phone_number")
        @Pattern(regexp = "\\+?[\\d\\- ]{10,16}", message = "Номер телефона должен содержать от 10 до 15 цифр, возможно с +. Для разделения можно использовать пробел или тире")
        String phoneNumber

){
}
