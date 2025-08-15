package ru.almaz.ticketservice.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.almaz.ticketservice.exception.InvalidAccessTokenException;
import ru.almaz.ticketservice.exception.InvalidRefreshTokenException;
import ru.almaz.ticketservice.service.JwtCacheService;

@Component
@RequiredArgsConstructor
public class JwtValidator {
    private final JwtCacheService jwtCacheService;

    public void isAccessTokenValid(String email, String token) {
        String tokenFromCache = jwtCacheService.getAccessToken(email);
        if(!tokenFromCache.equals(token)) {
            throw new InvalidAccessTokenException("Access токен невалидный");
        }
    }

    public void isRefreshTokenValid(String email, String token) {
        String tokenFromCache = jwtCacheService.getRefreshToken(email);
        if(!tokenFromCache.equals(token)) {
            throw new InvalidRefreshTokenException("Refresh токен невалидный");
        }
    }
}
