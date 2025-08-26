package ru.almaz.ticketservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.almaz.ticketservice.dto.route.RouteDto;
import ru.almaz.ticketservice.dto.route.AddRouteRequest;
import ru.almaz.ticketservice.dto.route.UpdateRouteRequest;
import ru.almaz.ticketservice.service.RouteService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/route")
public class RouteController {

    private final RouteService routeService;

    @Operation(
            summary = "Добавление маршрута",
            description = "Эндпоинт для добавления нового маршрута. Требуется роль ADMIN.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Маршрут успешно добавлен",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = RouteDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Ошибка валидации данных",
                            content = @Content(mediaType = "application/problem+json", schema = @Schema(implementation = ProblemDetail.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Перевозчик не найден",
                            content = @Content(mediaType = "application/problem+json", schema = @Schema(implementation = ProblemDetail.class))
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Недостаточно прав",
                            content = @Content(
                                    mediaType = "application/problem+json",
                                    examples = @ExampleObject(
                                            value = "{\n" +
                                                    "  \"status\": 403,\n" +
                                                    "  \"title\": \"Forbidden\",\n" +
                                                    "  \"detail\": \"Доступ запрещён\"\n" +
                                                    "}"
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Пользователь не авторизирован",
                            content = @Content(
                                    mediaType = "application/problem+json",
                                    examples = @ExampleObject(
                                            value = "{\n" +
                                                    "  \"status\": 401,\n" +
                                                    "  \"title\": \"Unauthorized\",\n" +
                                                    "  \"detail\": \"Пользователь не авторизирован\"\n" +
                                                    "}"
                                    )
                            )
                    )
            }
    )
    @PostMapping
    public ResponseEntity<RouteDto> addRoute(@RequestBody @Valid AddRouteRequest routeRequest) {
        RouteDto routeDto = routeService.saveRoute(routeRequest);
        return new ResponseEntity<>(routeDto, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Обновление маршрута",
            description = "Эндпоинт для обновления маршрута. Требуется роль ADMIN.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Маршрут успешно обновлен",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = RouteDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Ошибка валидации данных",
                            content = @Content(mediaType = "application/problem+json", schema = @Schema(implementation = ProblemDetail.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Маршрут/Перевозчик не найден",
                            content = @Content(mediaType = "application/problem+json", schema = @Schema(implementation = ProblemDetail.class))
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Недостаточно прав",
                            content = @Content(
                                    mediaType = "application/problem+json",
                                    examples = @ExampleObject(
                                            value = "{\n" +
                                                    "  \"status\": 403,\n" +
                                                    "  \"title\": \"Forbidden\",\n" +
                                                    "  \"detail\": \"Доступ запрещён\"\n" +
                                                    "}"
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Пользователь не авторизирован",
                            content = @Content(
                                    mediaType = "application/problem+json",
                                    examples = @ExampleObject(
                                            value = "{\n" +
                                                    "  \"status\": 401,\n" +
                                                    "  \"title\": \"Unauthorized\",\n" +
                                                    "  \"detail\": \"Пользователь не авторизирован\"\n" +
                                                    "}"
                                    )
                            )
                    )
            }
    )
    @PatchMapping("/{routeId}")
    public ResponseEntity<RouteDto> updateRoute(@PathVariable Long routeId,
                                @RequestBody @Valid UpdateRouteRequest routeRequest) {
        RouteDto routeDto = routeService.updateRoute(routeId, routeRequest);
        return new ResponseEntity<>(routeDto, HttpStatus.OK);
    }

    @Operation(
            summary = "Удаление маршрута",
            description = "Эндпоинт для удаления маршрута. Требуется роль ADMIN.",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Маршрут успешно удален"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Маршрут не найден",
                            content = @Content(mediaType = "application/problem+json", schema = @Schema(implementation = ProblemDetail.class))
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Недостаточно прав",
                            content = @Content(
                                    mediaType = "application/problem+json",
                                    examples = @ExampleObject(
                                            value = "{\n" +
                                                    "  \"status\": 403,\n" +
                                                    "  \"title\": \"Forbidden\",\n" +
                                                    "  \"detail\": \"Доступ запрещён\"\n" +
                                                    "}"
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Пользователь не авторизирован",
                            content = @Content(
                                    mediaType = "application/problem+json",
                                    examples = @ExampleObject(
                                            value = "{\n" +
                                                    "  \"status\": 401,\n" +
                                                    "  \"title\": \"Unauthorized\",\n" +
                                                    "  \"detail\": \"Невалидный токен\"\n" +
                                                    "}"
                                    )
                            )
                    )
            }
    )
    @DeleteMapping("/{routeId}")
    public ResponseEntity<Void> deleteRoute(@PathVariable Long routeId) {
        routeService.deleteRoute(routeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
