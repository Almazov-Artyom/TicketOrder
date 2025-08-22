package ru.almaz.ticketservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.almaz.ticketservice.dto.RouteDto;
import ru.almaz.ticketservice.dto.AddRouteRequest;
import ru.almaz.ticketservice.dto.UpdateRouteRequest;
import ru.almaz.ticketservice.service.RouteService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/route")
public class RouteController {

    private final RouteService routeService;

    @PostMapping
    public RouteDto addRoute(@RequestBody @Valid AddRouteRequest routeRequest) {
        return routeService.saveRoute(routeRequest);
    }

    @PatchMapping("/{routeId}")
    public RouteDto updateRoute(@PathVariable Long routeId,
                                @RequestBody @Valid UpdateRouteRequest routeRequest) {
        return routeService.updateRoute(routeId, routeRequest);
    }


}
