package ru.almaz.savedservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import ru.almaz.savedservice.entity.Ticket;

@Service
@RequiredArgsConstructor
public class KafkaConsumerService {

    @KafkaListener(topics = "tickets", groupId = "group1")
    public void listenVerifyEmail(@Payload String ticket) throws JsonProcessingException {
      ObjectMapper objectMapper = new ObjectMapper();
      Ticket ticketObject = objectMapper.readValue(ticket, Ticket.class);
        System.out.println();
    }
}
