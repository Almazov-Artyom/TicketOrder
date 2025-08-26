package ru.almaz.ticketservice.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequest(
        @NotBlank(message = "{email.blank}")
        @Email(message = "{email.invalid}")
        @Size(max = 255, message = "{email.size}")
        String email,

        @NotBlank(message = "{password.blank}")
        @Size(max = 255, message = "{password.size.max}")
        String password
) {
}
