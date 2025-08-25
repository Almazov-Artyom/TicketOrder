package ru.almaz.savedservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import ru.almaz.savedservice.entity.Ticket;

@Service
@RequiredArgsConstructor
public class KafkaConsumerService {

    @KafkaListener(topics = "${spring.kafka.consumer.topic}")
    @SneakyThrows
    public void listenTicket(@Payload String ticketString) {
        ObjectMapper mapper = new ObjectMapper();
        Ticket ticket = mapper.readValue(ticketString, Ticket.class);
    }
}

