package ru.almaz.ticketservice.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RefreshTokenRequest(
        @NotBlank(message = "Refresh Token не может быть пустым")
        @JsonProperty("refresh_token")
        @Size(max = 255, message = "Refresh_token не больше 255 символов")
        String refreshToken
) {
}
