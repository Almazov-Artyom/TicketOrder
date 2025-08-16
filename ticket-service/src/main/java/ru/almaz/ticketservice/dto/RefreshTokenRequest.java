package ru.almaz.ticketservice.dto;

import jakarta.validation.constraints.NotBlank;

public record RefreshTokenRequest(
        @NotBlank(message = "RefreshToken не может быть пустым")
        String refreshToken
) {
}
