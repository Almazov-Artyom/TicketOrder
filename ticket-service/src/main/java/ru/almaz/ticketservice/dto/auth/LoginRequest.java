package ru.almaz.ticketservice.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequest(
        @NotBlank(message = "Email не может быть пустым")
        @Email(message = "Неправильный email")
        @Size(max = 255, message = "Email не больше 255 символов")
        String email,

        @NotBlank(message = "Пароль не может быть пустым")
        @Size(max = 255, message = "Пароль не больше 255 символов")
        String password
) {
}
