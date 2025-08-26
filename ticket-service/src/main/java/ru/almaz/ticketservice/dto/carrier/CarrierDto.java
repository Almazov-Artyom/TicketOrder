package ru.almaz.ticketservice.dto.carrier;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

public record CarrierDto(
        @Schema(description = "Идентификатор перевозчика", examples = "1")
        Long id,

        @Schema(description = "Название перевозчика", examples = "РЖД")
        String name,

        @JsonProperty("phone_number")
        @Schema(description = "Номер телефона перевозчика", examples = "8-957-458-20-49")
        String phoneNumber
) {
}
