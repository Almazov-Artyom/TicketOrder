package ru.almaz.ticketservice.dto.carrier;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import ru.almaz.ticketservice.annotation.ColumnMapping;
import ru.almaz.ticketservice.annotation.NotBlankIfPresent;

public record UpdateCarrierDto(
        @NotBlankIfPresent(message = "{carrier.name.blank}")
        @Size(max = 255, message = "{carrier.name.size}")
        @Schema(description = "Название перевозчика", examples = "РЖД", maxLength = 255)
        String name,

        @JsonProperty("phone_number")
        @ColumnMapping("phone_number")
        @NotBlankIfPresent(message = "{carrier.phone.blank}")
        @Pattern(regexp = "\\+?[\\d\\- ]{10,16}", message = "{carrier.phone.invalid}")
        @Schema(description = "Номер телефона перевозчика", examples = "8-957-458-20-49")
        String phoneNumber
) {
}
