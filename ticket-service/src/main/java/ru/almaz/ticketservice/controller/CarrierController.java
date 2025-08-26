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
import ru.almaz.ticketservice.dto.carrier.CarrierDto;
import ru.almaz.ticketservice.dto.carrier.AddCarrierRequest;
import ru.almaz.ticketservice.dto.carrier.UpdateCarrierDto;
import ru.almaz.ticketservice.service.CarrierService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/carrier")
public class CarrierController {

    private final CarrierService carrierService;

    @Operation(
            summary = "Добавление перевозчика",
            description = "Эндпоинт для добавления нового перевозчика. Требуется роль ADMIN.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Перевозчик успешно добавлен",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CarrierDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Ошибка валидации данных",
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
    public ResponseEntity<CarrierDto> addCarrier(@RequestBody @Valid AddCarrierRequest carrierRequest) {
        CarrierDto carrierDto = carrierService.save(carrierRequest);
        return new ResponseEntity<>(carrierDto, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Обновление перевозчика",
            description = "Эндпоинт для обновления перевозчика. Требуется роль ADMIN.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Перевозчик успешно обновлен",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CarrierDto.class))
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
    @PatchMapping("/{carrierId}")
    public ResponseEntity<CarrierDto> updateCarrier(@PathVariable Long carrierId,
                                                    @RequestBody @Valid UpdateCarrierDto updateCarrierDto) {
        CarrierDto carrierDto = carrierService.updateCarrier(carrierId, updateCarrierDto);
        return new ResponseEntity<>(carrierDto, HttpStatus.OK);
    }

    @Operation(
            summary = "Удаление перевозчика",
            description = "Эндпоинт для удаления перевозчика. Требуется роль ADMIN.",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Перевозчик успешно удален"
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
                                                    "  \"detail\": \"Невалидный токен\"\n" +
                                                    "}"
                                    )
                            )
                    )
            }
    )
    @DeleteMapping("/{carrierId}")
    public ResponseEntity<Void> deleteCarrier(@PathVariable Long carrierId) {
        carrierService.deleteCarrier(carrierId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
