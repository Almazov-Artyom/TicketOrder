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
        if(tokenFromCache != null){
            if(tokenFromCache.equals(token)) {
                return;
            }
        }
        throw new InvalidAccessTokenException("access.token.invalid");
    }

    public void isRefreshTokenValid(String email, String token) {
        String tokenFromCache = jwtCacheService.getRefreshToken(email);
        if(tokenFromCache != null){
            if(tokenFromCache.equals(token)) {
                return;
            }
        }
        throw new InvalidRefreshTokenException("refresh.token.invalid");
    }
}
