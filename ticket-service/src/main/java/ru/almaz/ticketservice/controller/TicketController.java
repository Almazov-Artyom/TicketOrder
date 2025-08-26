package ru.almaz.ticketservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.almaz.ticketservice.dto.ticket.*;
import ru.almaz.ticketservice.service.TicketService;

import java.util.List;

@RestController
@RequestMapping("/ticket")
@RequiredArgsConstructor
public class TicketController {
    private final TicketService ticketService;

    @Operation(
            summary = "Получение доступных для покупки билетов",
            description = "Эндпоинт возвращает список доступных для покупки билетов, подходящих под переданные параметры",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Список доступных билетов",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = TicketInfo.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Ошибка валидации данных",
                            content = @Content(mediaType = "application/problem+json", schema = @Schema(implementation = ProblemDetail.class))
                    )
            }
    )
    @PostMapping("/available")
    @SecurityRequirements
    public ResponseEntity<List<TicketInfo>> getAvailableTickets(@RequestBody @Valid TicketFilter ticketFilter) {
        List<TicketInfo> availableTickets = ticketService.getAvailableTickets(ticketFilter);
        return new ResponseEntity<>(availableTickets, HttpStatus.OK);
    }

    @Operation(
            summary = "Покупка билета",
            description = "Эндпоинт для покупки билета по id.",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Билет успешно куплен"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Билет не найден",
                            content = @Content(mediaType = "application/problem+json", schema = @Schema(implementation = ProblemDetail.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Билет не доступен для покупки",
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
    @PostMapping("/buy/{ticketId}")
    public ResponseEntity<Void> buyTicket(@PathVariable Long ticketId) {
        ticketService.buyTicket(ticketId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(
            summary = "Получение списка купленных билетов",
            description = "Возвращает все билеты, приобретённые текущим авторизованным пользователем.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Список билетов успешно получен",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = TicketInfo.class))
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
    @GetMapping("/purchased")
    public ResponseEntity<List<TicketInfo>> getPurchasedTickets() {
        List<TicketInfo> allTicketsByUser = ticketService.getAllTicketsByUser();
        return new ResponseEntity<>(allTicketsByUser, HttpStatus.OK);
    }

    @Operation(
            summary = "Добавление билета",
            description = "Эндпоинт для добавления нового билета. Требуется роль ADMIN.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Билет успешно добавлен",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = TicketDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Ошибка валидации данных",
                            content = @Content(mediaType = "application/problem+json", schema = @Schema(implementation = ProblemDetail.class))
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
                                                    "  \"detail\": \"Пользователь не авторизирован\"\n" +
                                                    "}"
                                    )
                            )
                    )
            }
    )
    @PostMapping
    public ResponseEntity<TicketDto> addTicket(@RequestBody @Valid AddTicketRequest ticketRequest) {
        TicketDto ticketDto = ticketService.saveTicket(ticketRequest);
        return new ResponseEntity<>(ticketDto, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Обновление билета",
            description = "Эндпоинт для обновления билета. Требуется роль ADMIN.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Маршрут успешно обновлен",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = TicketDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Ошибка валидации данных",
                            content = @Content(mediaType = "application/problem+json", schema = @Schema(implementation = ProblemDetail.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Билет/Маршрут не найден",
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
    @PatchMapping("/{ticketId}")
    public ResponseEntity<TicketDto> updateTicket(@PathVariable Long ticketId,
                                                  @RequestBody @Valid UpdateTicketRequest ticketRequest) {
        TicketDto ticketDto = ticketService.updateTicket(ticketId, ticketRequest);
        return new ResponseEntity<>(ticketDto, HttpStatus.OK);
    }

    @Operation(
            summary = "Удаление билета",
            description = "Эндпоинт для удаления билета. Требуется роль ADMIN.",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Билет успешно удален"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Билет не найден",
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
    @DeleteMapping("/{ticketId}")
    public ResponseEntity<Void> deleteTicket(@PathVariable Long ticketId) {
        ticketService.deleteTicket(ticketId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
