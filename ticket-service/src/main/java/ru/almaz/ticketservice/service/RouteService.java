package ru.almaz.ticketservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.almaz.ticketservice.dao.RouteDao;
import ru.almaz.ticketservice.dto.RouteDto;
import ru.almaz.ticketservice.dto.AddRouteRequest;
import ru.almaz.ticketservice.dto.UpdateRouteRequest;
import ru.almaz.ticketservice.entity.Route;
import ru.almaz.ticketservice.mapper.RouteMapper;
import ru.almaz.ticketservice.validator.RouteValidator;

@Service
@RequiredArgsConstructor
public class RouteService {

    private final RouteDao routeDao;

    private final RouteMapper routeMapper;

    private final RouteValidator routeValidator;

    @Transactional
    public RouteDto saveRoute(AddRouteRequest routeRequest) {
        Route route = routeMapper.toRoute(routeRequest);
        routeDao.save(route);
        return routeMapper.toRouteDto(route);
    }

    public RouteDto updateRoute(Long routeId, UpdateRouteRequest routeRequest) {
        routeValidator.isRouteValid(routeId);
        Route route = routeDao.updateRoute(routeId, routeRequest);
        return routeMapper.toRouteDto(route);
    }
}
