package ru.almaz.ticketservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.stereotype.Component;
import ru.almaz.ticketservice.dto.RouteDto;
import ru.almaz.ticketservice.dto.RouteRequest;
import ru.almaz.ticketservice.entity.Carrier;
import ru.almaz.ticketservice.entity.Route;

import java.time.Duration;

@Component
public class RouteMapper {

    public Route toRoute(RouteRequest routeRequest) {
        Carrier carrier = new Carrier();
        carrier.setId(routeRequest.carrierId());

        Route route = new Route();
        route.setOrigin(routeRequest.origin());
        route.setDestination(routeRequest.destination());
        route.setCarrier(carrier);
        route.setDuration(routeRequest.duration());

        return route;
    }

    public RouteDto toRouteDto(Route route) {
        Long id = route.getId();
        String origin = route.getOrigin();
        String destination = route.getDestination();
        Long carrierId = route.getCarrier().getId();
        Duration duration = route.getDuration();

        return new RouteDto(id, origin, destination, carrierId, duration);
    }



}

