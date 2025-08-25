package ru.almaz.ticketservice.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.almaz.ticketservice.entity.Ticket;
import ru.almaz.ticketservice.enums.TicketStatus;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {
    @Value("${spring.kafka.producer.ticket.topic.name}")
    private String ticketTopicName;

    private final KafkaTemplate<String, Ticket> kafkaTemplate;

    public void sendTicket(Ticket ticket) {
        kafkaTemplate.send(ticketTopicName, ticket);
    }
}
