package ru.almaz.ticketservice.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RefreshTokenRequest(
        @NotBlank(message = "{refresh.token.blank}")
        @JsonProperty("refresh_token")
        @Size(max = 255, message = "{refresh.token.size}")
        @Schema(description = "Токен для обновления токена доступа")
        String refreshToken
) {
}
