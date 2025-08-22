package ru.almaz.ticketservice.dto.auth;

public record RefreshTokenResponse(
        String accessToken,

        String refreshToken
) {
}
