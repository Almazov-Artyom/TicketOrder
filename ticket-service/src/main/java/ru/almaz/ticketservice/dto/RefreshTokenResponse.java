package ru.almaz.ticketservice.dto;

public record RefreshTokenResponse(
        String accessToken,

        String refreshToken
) {
}
