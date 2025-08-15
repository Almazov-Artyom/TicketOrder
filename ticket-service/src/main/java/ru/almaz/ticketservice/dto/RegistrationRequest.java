package ru.almaz.ticketservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegistrationRequest(
        @NotBlank(message = "Email не может быть пустым")
        @Email(message = "Неправильный email")
        String email,

        @NotBlank(message = "Пароль не может быть пустым")
        String password,

        @NotBlank(message = "Фамилия не можеть быть пуст")
        String lastName,

        @NotBlank(message = "Имя не может быть пустым")
        String firstName,

        @NotBlank(message = "Отчество не может быть пустым")
        String middleName
) {
}
