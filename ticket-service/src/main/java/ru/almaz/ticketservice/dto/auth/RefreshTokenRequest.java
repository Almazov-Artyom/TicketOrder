package ru.almaz.ticketservice.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RefreshTokenRequest(
        @NotBlank(message = "{refresh.token.blank}")
        @JsonProperty("refresh_token")
        @Size(max = 255, message = "{refresh.token.size}")
        String refreshToken
) {
}
