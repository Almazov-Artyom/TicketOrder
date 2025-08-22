package ru.almaz.ticketservice.dto.auth;

public record LoginResponse(
        String accessToken,

        String refreshToken
) {
}
