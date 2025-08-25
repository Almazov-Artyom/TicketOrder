package ru.almaz.ticketservice.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import ru.almaz.ticketservice.annotation.NotBlankIfPresent;

public record RegistrationRequest(
        @NotBlank(message = "Email не может быть пустым")
        @Email(message = "Неправильный email")
        @Size(max = 255, message = "Email не больше 255 символов")
        String email,

        @NotBlank(message = "Пароль не может быть пустым")
        @Size(min=6, max = 255, message = "Пароль должен быть от 6 до 255 символов")
        String password,

        @NotBlank(message = "Фамилия не можеть быть пуст")
        @JsonProperty("last_name")
        @Size(max = 255, message = "Фамилия не больше 255 символов")
        String lastName,

        @NotBlank(message = "Имя не может быть пустым")
        @JsonProperty("first_name")
        @Size(max = 255, message = "Имя не больше 255 символов")
        String firstName,

        @NotBlankIfPresent(message = "Отчество не может быть пустым")
        @JsonProperty("middle_name")
        @Size(max = 255, message = "Отчество не больше 255 символов")
        String middleName
) {
}
