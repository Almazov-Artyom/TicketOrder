package ru.almaz.ticketservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import ru.almaz.ticketservice.entity.Ticket;

import java.time.Duration;

@Configuration
@EnableCaching
public class RedisConfig {
    @Value("${cache.access-token.name}")
    private String accessTokenCacheName;

    @Value("${cache.refresh-token.name}")
    private String refreshTokenCacheName;

    @Value("${spring.access.jwt.ttl}")
    private Duration accessTokenTtl;

    @Value("${spring.refresh.jwt.ttl}")
    private Duration refreshTokenTtl;

    @Bean
    public RedisCacheManager redisCacheManager(RedisConnectionFactory connectionFactory) {
        return RedisCacheManager.builder(connectionFactory)
                .withCacheConfiguration(
                        accessTokenCacheName,
                        RedisCacheConfiguration
                                .defaultCacheConfig()
                                .entryTtl(accessTokenTtl)
                )
                .withCacheConfiguration(
                        refreshTokenCacheName,
                        RedisCacheConfiguration
                                .defaultCacheConfig()
                                .entryTtl(refreshTokenTtl)
                )
                .build();
    }

    @Bean
    public RedisTemplate<String, Ticket> ticketRedisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Ticket> template = new RedisTemplate<>();

        template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());

        return template;
    }
}
