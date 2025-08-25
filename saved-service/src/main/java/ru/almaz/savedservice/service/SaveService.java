package ru.almaz.savedservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.almaz.savedservice.dao.CarrierDao;
import ru.almaz.savedservice.dao.RouteDao;
import ru.almaz.savedservice.dao.TicketDao;
import ru.almaz.savedservice.entity.Carrier;
import ru.almaz.savedservice.entity.Route;
import ru.almaz.savedservice.entity.Ticket;

@Service
@RequiredArgsConstructor
public class SaveService {

    private final CarrierDao carrierDao;

    private final RouteDao routeDao;

    private final TicketDao ticketDao;

    @Transactional
    public void saveTicket(Ticket ticket) {
        Route route = ticket.getRoute();

        Carrier carrier = route.getCarrier();

        Carrier savedCarrier = carrierDao.save(carrier);

        route.setCarrier(savedCarrier);

        Route saveRoute = routeDao.save(route);

        ticket.setRoute(saveRoute);

        ticketDao.save(ticket);
    }
}
