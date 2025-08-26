package ru.almaz.ticketservice.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import ru.almaz.ticketservice.annotation.NotBlankIfPresent;

public record RegistrationRequest(
        @NotBlank(message = "{email.blank}")
        @Email(message = "{email.invalid}")
        @Size(max = 255, message = "{email.size}")
        @Schema(description = "Электронная почта пользователя", examples = "user@email.com", maxLength = 255)
        String email,

        @NotBlank(message = "{password.blank}")
        @Size(min = 6, max = 255, message = "{password.size}")
        @Schema(description = "Пароль пользователя", examples = "password", minLength = 6, maxLength = 255)
        String password,

        @NotBlank(message = "{last.name.blank}")
        @JsonProperty("last_name")
        @Size(max = 255, message = "{last.name.size}")
        @Schema(description = "Фамилия пользователя", examples = "Иванов", maxLength = 255)
        String lastName,

        @NotBlank(message = "{first.name.blank}")
        @JsonProperty("first_name")
        @Size(max = 255, message = "{first.name.size}")
        @Schema(description = "Имя пользователя", examples = "Иван", maxLength = 255)
        String firstName,

        @NotBlankIfPresent(message = "{middle.name.blank}")
        @JsonProperty("middle_name")
        @Size(max = 255, message = "{middle.name.size}")
        @Schema(description = "Отчество пользователя", examples = "Иванович", maxLength = 255)
        String middleName
) {
}
