package ru.almaz.ticketservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.almaz.ticketservice.dto.ticket.SendTicket;
import ru.almaz.ticketservice.dto.ticket.TicketInfo;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {
    @Value("${spring.kafka.producer.ticket.topic.name}")
    private String ticketTopicName;

    private final KafkaTemplate<String, String> kafkaTemplate;

    @SneakyThrows
    public void sendTicket(SendTicket ticket) {
        ObjectMapper mapper = new ObjectMapper();
        String ticketString = mapper.writeValueAsString(ticket);
        kafkaTemplate.send(ticketTopicName, ticketString);
    }
}
