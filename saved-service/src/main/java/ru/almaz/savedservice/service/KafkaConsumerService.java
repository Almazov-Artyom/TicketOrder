package ru.almaz.savedservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import ru.almaz.savedservice.dao.TicketDao;
import ru.almaz.savedservice.entity.TicketInfo;

@Service
@RequiredArgsConstructor
public class KafkaConsumerService {

    private final TicketDao ticketDao;

    @KafkaListener(topics = "${spring.kafka.consumer.topic}")
    @SneakyThrows
    public void listenTicket(@Payload String ticketString) {
        ObjectMapper mapper = new ObjectMapper();
        TicketInfo ticket = mapper.readValue(ticketString, TicketInfo.class);
        ticketDao.save(ticket);
    }
}

