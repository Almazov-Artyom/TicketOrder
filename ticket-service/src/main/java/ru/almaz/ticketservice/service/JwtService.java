package ru.almaz.ticketservice.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.almaz.ticketservice.entity.User;
import ru.almaz.ticketservice.exception.InvalidAccessTokenException;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    @Value("${spring.jwt.secret}")
    private String secret;

    @Value("${spring.access.jwt.ttl}")
    private Duration accessTokenTtl;

    @Value("${spring.refresh.jwt.ttl}")
    private Duration refreshTokenTtl;


    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private String generateToken(UserDetails userDetails, Duration lifetime) {
        Map<String, Object> claims = new HashMap<>();
        String subject = "";
        if (userDetails instanceof User customUserDetails) {
            claims.put("role", customUserDetails.getRole().toString());
            subject = customUserDetails.getId().toString();
        }
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + lifetime.toMillis()))
                .signWith(getSigningKey())
                .compact();
    }

    private <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (Exception e) {
            throw new InvalidAccessTokenException("Невалидный токен");
        }
    }

    public String generateAccessToken(UserDetails userDetails) {
        return generateToken(userDetails, accessTokenTtl);
    }

    public String generateRefreshToken(UserDetails userDetails) {
        return generateToken(userDetails, refreshTokenTtl);
    }

    public String extractUserId(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    public String extractRole(String token) {
        return extractClaims(token, claims -> claims.get("role").toString());
    }

}
