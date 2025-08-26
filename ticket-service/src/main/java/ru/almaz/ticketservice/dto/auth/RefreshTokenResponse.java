package ru.almaz.ticketservice.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

public record RefreshTokenResponse(
        @JsonProperty("access_token")
        @Schema(description = "Токен доступа", examples = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiVVNFUiIsInN1YiI6IjMiLCJpYXQiOjE3NTYyMjk2MDMsImV4cCI6MTc1NjIzNTYwM30.ra6Lz13WHhcRs7tf4q2MynWJ4RFSwiSU0163FaovjKM")
        String accessToken,

        @JsonProperty("refresh_token")
        @Schema(description = "Токен обновления", examples = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiVVNFUiIsInN1YiI6IjMiLCJpYXQiOjE3NTYyMjk2MDMsImV4cCI6MTc1NjIzNTYwM30.ra6Lz13WHhcRs7tf4q2MynWJ4RFSwiSU0163FaovjKM")
        String refreshToken
) {
}
