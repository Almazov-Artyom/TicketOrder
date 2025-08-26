package ru.almaz.ticketservice.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.almaz.ticketservice.dao.RouteDao;
import ru.almaz.ticketservice.exception.RouteNotFoundException;

@Component
@RequiredArgsConstructor
public class RouteValidator {
    private final RouteDao routeDao;

    public void isRouteValid(Long routeId) {
        if(!routeDao.existRoute(routeId)) {
            throw new RouteNotFoundException("route.not.found");
        }
    }

}
