package ru.almaz.ticketservice.validator;

import org.springframework.stereotype.Component;
import ru.almaz.ticketservice.dto.TicketFilter;
import ru.almaz.ticketservice.exception.InvalidDepartureTimeException;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Component
public class TicketFilterValidator {
    public void dateValidation(TicketFilter ticketFilter) {
        LocalDate departureDate = ticketFilter.getDepartureDate();
        LocalTime departureTime = ticketFilter.getDepartureTime();

        LocalDateTime departureDateTime = LocalDateTime.now();

        if (departureDate != null && departureTime == null) {
            if (departureDate.isBefore(LocalDate.now()))
                throw new InvalidDepartureTimeException("Дата/время не валидны");
            departureDateTime = departureDate.equals(LocalDate.now()) ?
                    LocalDateTime.now() : departureDate.atStartOfDay();
        }

        if (departureDate != null && departureTime != null) {
            LocalDateTime localDateTime = LocalDateTime.of(departureDate, departureTime);
            if (localDateTime.isBefore(LocalDateTime.now()))
                throw new InvalidDepartureTimeException("Дата/время не валидны");
            departureDateTime = localDateTime;
        }

        if (departureDate == null && departureTime != null) {
            if(departureTime.isBefore(LocalTime.now()))
                throw new InvalidDepartureTimeException("Дата/время не валидны");
            departureDateTime = LocalDateTime.of(LocalDate.now(), departureTime);
        }

        ticketFilter.setDepartureDateTime(Timestamp.valueOf(departureDateTime));
    }
}
