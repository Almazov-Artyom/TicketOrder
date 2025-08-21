package ru.almaz.ticketservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.almaz.ticketservice.dao.UserTicketDao;
import ru.almaz.ticketservice.dto.TicketDto;
import ru.almaz.ticketservice.entity.Ticket;
import ru.almaz.ticketservice.mapper.TicketMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserTicketService {

    private final UserTicketDao userTicketDao;

    private final UserService userService;

    private final TicketMapper ticketMapper;

    @Transactional
    public List<TicketDto> getAllTicketsByUser() {
        Long userId = userService.getCurrentUserId();

        List<Ticket> allTicketsByUserId = userTicketDao.findAllTicketsByUserId(userId);

        return allTicketsByUserId
                .stream()
                .map(ticketMapper::toDto)
                .toList();

    }
}
