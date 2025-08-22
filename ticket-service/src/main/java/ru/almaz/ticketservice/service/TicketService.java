package ru.almaz.ticketservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.almaz.ticketservice.dao.TicketDao;
import ru.almaz.ticketservice.dao.UserTicketDao;
import ru.almaz.ticketservice.dto.ticket.TicketDto;
import ru.almaz.ticketservice.dto.ticket.TicketFilter;
import ru.almaz.ticketservice.dto.ticket.AddTicketRequest;
import ru.almaz.ticketservice.dto.ticket.TicketResponse;
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
    public TicketResponse saveTicket(AddTicketRequest ticketRequest) {
        Ticket ticket = ticketMapper.toTicket(ticketRequest);
        ticket.setStatus(TicketStatus.AVAILABLE);
        ticketDao.save(ticket);
        return ticketMapper.toTicketResponse(ticket);
    }
}
