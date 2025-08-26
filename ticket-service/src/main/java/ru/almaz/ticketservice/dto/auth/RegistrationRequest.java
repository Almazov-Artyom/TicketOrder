package ru.almaz.ticketservice.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import ru.almaz.ticketservice.annotation.NotBlankIfPresent;

public record RegistrationRequest(
        @NotBlank(message = "{email.blank}")
        @Email(message = "{email.invalid}")
        @Size(max = 255, message = "{email.size}")
        String email,

        @NotBlank(message = "{password.blank}")
        @Size(min=6, max = 255, message = "{password.size}")
        String password,

        @NotBlank(message = "{last.name.blank}")
        @JsonProperty("last_name")
        @Size(max = 255, message = "{last.name.size}")
        String lastName,

        @NotBlank(message = "{first.name.blank}")
        @JsonProperty("first_name")
        @Size(max = 255, message = "{first.name.size}")
        String firstName,

        @NotBlankIfPresent(message = "{middle.name.blank}")
        @JsonProperty("middle_name")
        @Size(max = 255, message = "{middle.name.size}")
        String middleName
) {
}
