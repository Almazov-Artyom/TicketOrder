package ru.almaz.ticketservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.almaz.ticketservice.dao.TicketDao;
import ru.almaz.ticketservice.dao.UserTicketDao;
import ru.almaz.ticketservice.dto.ticket.*;
import ru.almaz.ticketservice.entity.Ticket;
import ru.almaz.ticketservice.enums.TicketStatus;
import ru.almaz.ticketservice.exception.TicketUnavailableException;
import ru.almaz.ticketservice.mapper.TicketMapper;
import ru.almaz.ticketservice.validator.RouteValidator;
import ru.almaz.ticketservice.validator.TicketValidator;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketService {
    private final TicketDao ticketDao;

    private final UserService userService;

    private final UserTicketDao userTicketDao;

    private final TicketMapper ticketMapper;

    private final TicketValidator ticketValidator;

    private final RouteValidator routeValidator;

    @Transactional
    public List<TicketDto> getAvailableTickets(TicketFilter ticketFilter) {
        ticketValidator.dateValidation(ticketFilter);

        List<Ticket> allAvailableTickets = ticketDao.findAllAvailableTickets(ticketFilter);

        return allAvailableTickets
                .stream()
                .map(ticketMapper::toDto)
                .toList();
    }

    @Transactional
    public void buyTicket(Long ticketId) {
        ticketValidator.isTicketForPurchaseValid(ticketId);

        Long userId = userService.getCurrentUserId();

        ticketDao.updateTicketStatus(ticketId);

        userTicketDao.save(userId, ticketId);
    }

    @Transactional
    public TicketResponse saveTicket(AddTicketRequest ticketRequest) {
        routeValidator.isRouteValid(ticketRequest.routeId());

        Ticket ticket = ticketMapper.toTicket(ticketRequest);
        ticket.setStatus(TicketStatus.AVAILABLE);

        ticketDao.save(ticket);

        return ticketMapper.toTicketResponse(ticket);
    }

    @Transactional
    public TicketResponse updateTicket(Long ticketId, UpdateTicketRequest ticketRequest) {
        ticketValidator.isTickedValid(ticketId);

        if (ticketRequest.routeId() != null)
            routeValidator.isRouteValid(ticketRequest.routeId());

        Ticket ticket = ticketDao.updateTicket(ticketId, ticketRequest);

        return ticketMapper.toTicketResponse(ticket);
    }

    @Transactional
    public void deleteTicket(Long ticketId) {
        ticketValidator.isTickedValid(ticketId);
        ticketDao.deleteTicket(ticketId);
    }
}
