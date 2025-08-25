package ru.almaz.ticketservice.dto.carrier;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import ru.almaz.ticketservice.annotation.ColumnMapping;
import ru.almaz.ticketservice.annotation.NotBlankIfPresent;

public record UpdateCarrierDto (
        @NotBlankIfPresent(message = "Название перевозчика не может быть пустым")
        @Size(max = 255, message = "Название не больше 255 символов")
        String name,

        @JsonProperty("phone_number")
        @ColumnMapping("phone_number")
        @NotBlankIfPresent(message = "Номер телефона не может быть пустым")
        @Pattern(regexp = "\\+?[\\d\\- ]{10,16}", message = "Номер телефона должен содержать от 10 до 15 цифр, возможно с +. Для разделения можно использовать пробел или тире")
        String phoneNumber
){
}
