package ru.almaz.ticketservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record RefreshTokenRequest(
        @NotBlank(message = "RefreshToken не может быть пустым")
        @JsonProperty("refresh_token")
        String refreshToken
) {
}
