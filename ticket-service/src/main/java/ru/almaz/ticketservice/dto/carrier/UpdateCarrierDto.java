package ru.almaz.ticketservice.dto.carrier;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import ru.almaz.ticketservice.annotation.ColumnMapping;
import ru.almaz.ticketservice.annotation.NotBlankIfPresent;

public record UpdateCarrierDto (
        @NotBlankIfPresent(message = "{carrier.name.blank}")
        @Size(max = 255, message = "{carrier.name.size}")
        String name,

        @JsonProperty("phone_number")
        @ColumnMapping("phone_number")
        @NotBlankIfPresent(message = "{carrier.phone.blank}")
        @Pattern(regexp = "\\+?[\\d\\- ]{10,16}", message = "{carrier.phone.invalid}")
        String phoneNumber
){
}
