package ru.almaz.ticketservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.almaz.ticketservice.dao.RouteDao;
import ru.almaz.ticketservice.dto.RouteDto;
import ru.almaz.ticketservice.dto.AddRouteRequest;
import ru.almaz.ticketservice.entity.Route;
import ru.almaz.ticketservice.mapper.RouteMapper;

@Service
@RequiredArgsConstructor
public class RouteService {

    private final RouteDao routeDao;

    private final RouteMapper routeMapper;

    @Transactional
    public RouteDto saveRoute(AddRouteRequest routeRequest) {
        Route route = routeMapper.toRoute(routeRequest);
        routeDao.save(route);
        return routeMapper.toRouteDto(route);
    }
}
