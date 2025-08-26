package ru.almaz.ticketservice.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;

public record RegistrationResponse(
        @Schema(description = "Сообщение о результате регистрации")
        String message
) {
}
