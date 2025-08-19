package ru.almaz.ticketservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtCacheService {
    private final CacheManager cacheManager;

    @Value("${cache.access-token.name}")
    private String accessTokenCacheName;

    @Value("${cache.refresh-token.name}")
     private String refreshTokenCacheName;

    public void putAccessToken(String userId, String accessToken) {
        Cache accessTokenCache = cacheManager.getCache(accessTokenCacheName);
        if (accessTokenCache != null) {
            accessTokenCache.put(userId, accessToken);
        }
    }

    public void putRefreshToken(String userId, String refreshToken) {
        Cache accessTokenCache = cacheManager.getCache(refreshTokenCacheName);
        if (accessTokenCache != null) {
            accessTokenCache.put(userId, refreshToken);
        }
    }

    public String getAccessToken(String userId) {
        Cache accessTokenCache = cacheManager.getCache(accessTokenCacheName);
        String accessToken = null;
        if (accessTokenCache != null) {
            accessToken = accessTokenCache.get(userId, String.class);
        }
        return accessToken;
    }

    public String getRefreshToken(String userId) {
        Cache accessTokenCache = cacheManager.getCache(refreshTokenCacheName);
        String refreshToken = null;
        if (accessTokenCache != null) {
            refreshToken = accessTokenCache.get(userId, String.class);
        }
        return refreshToken;
    }
}
