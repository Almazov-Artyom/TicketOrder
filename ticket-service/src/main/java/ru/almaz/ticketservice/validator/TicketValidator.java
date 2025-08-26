package ru.almaz.ticketservice.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.almaz.ticketservice.dao.TicketDao;
import ru.almaz.ticketservice.dto.ticket.TicketFilter;
import ru.almaz.ticketservice.exception.InvalidDepartureTimeException;
import ru.almaz.ticketservice.exception.TicketNotFoundException;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Component
@RequiredArgsConstructor
public class TicketValidator {

    private final TicketDao ticketDao;

    public void dateValidation(TicketFilter ticketFilter) {
        LocalDate departureDate = ticketFilter.getDepartureDate();
        LocalTime departureTime = ticketFilter.getDepartureTime();

        LocalDateTime departureDateTime = LocalDateTime.now();

        if (departureDate != null && departureTime == null) {
            if (departureDate.isBefore(LocalDate.now()))
                throw new InvalidDepartureTimeException("ticket.departure.time.invalid");
            departureDateTime = departureDate.equals(LocalDate.now()) ?
                    LocalDateTime.now() : departureDate.atStartOfDay();
        }

        if (departureDate != null && departureTime != null) {
            LocalDateTime localDateTime = LocalDateTime.of(departureDate, departureTime);
            if (localDateTime.isBefore(LocalDateTime.now()))
                throw new InvalidDepartureTimeException("ticket.departure.time.invalid");
            departureDateTime = localDateTime;
        }

        if (departureDate == null && departureTime != null) {
            if(departureTime.isBefore(LocalTime.now()))
                throw new InvalidDepartureTimeException("ticket.departure.time.invalid");
            departureDateTime = LocalDateTime.of(LocalDate.now(), departureTime);
        }

        ticketFilter.setDepartureDateTime(Timestamp.valueOf(departureDateTime));
    }

    public void isTickedValid(Long ticketId){
        if(!ticketDao.existTicket(ticketId))
            throw new TicketNotFoundException("ticket.not.found");
    }
}
