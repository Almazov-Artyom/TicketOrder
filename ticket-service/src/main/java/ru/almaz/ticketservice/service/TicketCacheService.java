package ru.almaz.ticketservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import ru.almaz.ticketservice.entity.Ticket;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketCacheService {
    @Value("${cache.ticket.name}")
    private String ticketCacheName;

    private final RedisTemplate<String, Ticket> redisTemplate;

    private String createKey(Long userId) {
        return ticketCacheName + ":" + userId;
    }

    public void putTicket(Long userId, Ticket ticket) {
        String key = createKey(userId);
        redisTemplate.opsForList().rightPush(key, ticket);

    }

    public void putTickets(Long userId, List<Ticket> tickets) {
        if (tickets == null || tickets.isEmpty()) return;

        String key = createKey(userId);

        redisTemplate.opsForList().rightPushAll(key, tickets);
    }

    public List<Ticket> getPurchasedTickets(Long userId) {
        String key = createKey(userId);
        List<Ticket> tickets = redisTemplate.opsForList().range(key, 0, -1);
        return tickets == null ? Collections.emptyList() : tickets;
    }

}
