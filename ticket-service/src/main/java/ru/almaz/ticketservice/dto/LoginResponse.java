package ru.almaz.ticketservice.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginResponse(
        String accessToken,

        String refreshToken
) {
}
