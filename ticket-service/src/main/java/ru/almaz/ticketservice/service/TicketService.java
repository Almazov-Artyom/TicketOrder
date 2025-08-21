package ru.almaz.ticketservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.almaz.ticketservice.dao.TicketDao;
import ru.almaz.ticketservice.dao.UserTicketDao;
import ru.almaz.ticketservice.dto.TicketDto;
import ru.almaz.ticketservice.dto.TicketFilter;
import ru.almaz.ticketservice.dto.TicketRequest;
import ru.almaz.ticketservice.dto.TicketResponse;
import ru.almaz.ticketservice.entity.Carrier;
import ru.almaz.ticketservice.entity.Route;
import ru.almaz.ticketservice.entity.Ticket;
import ru.almaz.ticketservice.enums.TicketStatus;
import ru.almaz.ticketservice.exception.TicketUnavailableException;
import ru.almaz.ticketservice.mapper.TicketMapper;
import ru.almaz.ticketservice.validator.TicketFilterValidator;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketService {
    private final TicketDao ticketDao;

    private final TicketFilterValidator ticketFilterValidator;

    private final UserService userService;

    private final UserTicketDao userTicketDao;

    private final TicketMapper ticketMapper;

    @Transactional
    public List<TicketDto> getAvailableTickets(TicketFilter ticketFilter) {
        ticketFilterValidator.dateValidation(ticketFilter);

        List<Ticket> allAvailableTickets = ticketDao.findAllAvailableTickets(ticketFilter);

        return allAvailableTickets
                .stream()
                .map(ticketMapper::toDto)
                .toList();
    }

    @Transactional
    public void buyTicket(Long ticketId) {
        if(!ticketDao.existTicketForPurchase(ticketId)) {
            throw new TicketUnavailableException("Билет не доступен для покупки");
        }
        Long userId = userService.getCurrentUserId();

        ticketDao.updateTicketStatus(ticketId);

        userTicketDao.save(userId, ticketId);
    }

    @Transactional
    public TicketResponse saveTicket(TicketRequest ticketRequest) {
        Ticket ticket = ticketMapper.toTicket(ticketRequest);
        ticket.setStatus(TicketStatus.AVAILABLE);
        ticketDao.save(ticket);
        return ticketMapper.toTicketResponse(ticket);
    }
}
