package ru.almaz.ticketservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

    @PostMapping
    public RouteDto addRoute(@RequestBody @Valid AddRouteRequest routeRequest) {
        return routeService.saveRoute(routeRequest);
    }

    @PatchMapping("/{routeId}")
    public RouteDto updateRoute(@PathVariable Long routeId,
                                @RequestBody @Valid UpdateRouteRequest routeRequest) {
        System.out.println(routeRequest);
        return routeService.updateRoute(routeId, routeRequest);
    }

    @DeleteMapping("/{routeId}")
    public void deleteRoute(@PathVariable Long routeId) {
        routeService.deleteRoute(routeId);
    }

}
