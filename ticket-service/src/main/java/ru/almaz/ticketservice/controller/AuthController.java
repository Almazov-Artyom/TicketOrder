package ru.almaz.ticketservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.almaz.ticketservice.dto.auth.*;
import ru.almaz.ticketservice.service.AuthService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(
            summary = "Регистрация пользователя",
            description = "Эндпоинт для регистрации нового пользователя",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Пользователь успешно зарегистрирован",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = RegistrationResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Ошибка валидации данных",
                            content = @Content(mediaType = "application/problem+json", schema = @Schema(implementation = ProblemDetail.class))
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Пользователь с таким email уже зарегистрирован",
                            content = @Content(mediaType = "application/problem+json", schema = @Schema(implementation = ProblemDetail.class))
                    )
            }
    )
    @PostMapping("/register")
    @SecurityRequirements
    public ResponseEntity<RegistrationResponse> register(@RequestBody @Valid RegistrationRequest registrationRequest) {
        RegistrationResponse registrationResponse = authService.registration(registrationRequest);
        return new ResponseEntity<>(registrationResponse, HttpStatus.CREATED);

    }

    @Operation(
            summary = "Авторизация пользователя",
            description = "Эндпоинт для авторизации пользователя с использованием email и пароля. Возвращает JWT токены.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Пользователь успешно авторизован",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoginResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Ошибка валидации данных",
                            content = @Content(mediaType = "application/problem+json", schema = @Schema(implementation = ProblemDetail.class))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Неверный email или пароль",
                            content = @Content(mediaType = "application/problem+json", schema = @Schema(implementation = ProblemDetail.class))
                    )
            }
    )
    @PostMapping("/login")
    @SecurityRequirements
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        LoginResponse loginResponse = authService.login(loginRequest);
        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }

    @Operation(
            summary = "Обновление токенов (refresh token)",
            description = "Эндпоинт для обновления JWT токенов. Требуется передать refresh token.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Токены успешно обновлены",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = RefreshTokenResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Ошибка валидации данных",
                            content = @Content(mediaType = "application/problem+json", schema = @Schema(implementation = ProblemDetail.class))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Неверный или просроченный refresh token",
                            content = @Content(mediaType = "application/problem+json", schema = @Schema(implementation = ProblemDetail.class))
                    )
            }
    )
    @PostMapping("/refresh")
    @SecurityRequirements
    public ResponseEntity<RefreshTokenResponse> refresh(@RequestBody @Valid RefreshTokenRequest refreshTokenRequest) {
        RefreshTokenResponse refreshTokenResponse = authService.refreshToken(refreshTokenRequest);
        return new ResponseEntity<>(refreshTokenResponse, HttpStatus.OK);
    }
}
