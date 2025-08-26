package ru.almaz.ticketservice.dto.carrier;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record AddCarrierRequest(
        @NotBlank(message = "{carrier.name.blank}")
        @Size(max = 255, message = "{carrier.name.size}")
        String name,

        @NotBlank(message = "{carrier.phone.blank}")
        @JsonProperty("phone_number")
        @Pattern(regexp = "\\+?[\\d\\- ]{10,16}", message = "{carrier.phone.invalid}")
        String phoneNumber

){
}
