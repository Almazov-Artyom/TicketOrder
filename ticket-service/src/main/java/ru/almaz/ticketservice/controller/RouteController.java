package ru.almaz.ticketservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.almaz.ticketservice.dto.RouteDto;
import ru.almaz.ticketservice.dto.RouteRequest;
import ru.almaz.ticketservice.service.RouteService;

@RestController
@RequiredArgsConstructor
public class RouteController {

    private final RouteService routeService;

    @PostMapping
    public RouteDto addRoute(@RequestBody @Valid RouteRequest routeRequest) {
        return routeService.save(routeRequest);
    }
}
