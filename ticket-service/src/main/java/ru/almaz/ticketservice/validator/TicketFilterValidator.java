package ru.almaz.ticketservice.validator;

import org.springframework.stereotype.Component;
import ru.almaz.ticketservice.dto.TicketFilter;
import ru.almaz.ticketservice.exception.InvalidDepartureTimeException;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class TicketFilterValidator {
    public void dateValidation(TicketFilter ticketFilter) {
        Timestamp timestamp = ticketFilter.getDepartureTime();
        if (timestamp != null) {
            Timestamp now = new Timestamp(System.currentTimeMillis());
            if (timestamp.before(now)) {
                throw new InvalidDepartureTimeException("Дата/время не может быть в прошлом");
            }
        }
        LocalDate today = LocalDate.now();
        ticketFilter.setDepartureTime(Timestamp.valueOf(today.atStartOfDay()));

    }
}
