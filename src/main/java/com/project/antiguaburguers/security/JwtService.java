package com.project.antiguaburguers.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtService {

    private final Key key;
    private final long expirationMs;
    private final long expirationRefreshMinutes;

    public JwtService(
            @Value("${security.jwt.secret}") String secret,
            @Value("${security.jwt.expiration-minutes}") long expirationMinutes,
            @Value("${security.jwt.expiration-refresh-minutes}") long expirationRefreshMinutes
    ) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expirationMs = expirationMinutes * 60_000;
        this.expirationRefreshMinutes = expirationRefreshMinutes + 60_000;
    }

    private String generateToken(String username, String role, Long time) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + time);

        return Jwts.builder()
                .setSubject(username)
                .setPayload('{' + role + '}')
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateToken(String username, String role) {
        return generateToken(username, role, expirationMs);
    }

    public String generateRefreshToken(String username, String role) {
        return generateToken(username, role, expirationRefreshMinutes);
    }

    public String extractUsername(String token) {
        return parseClaims(token).getBody().getSubject();
    }

    public boolean isValid(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private Jws<Claims> parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
    }
}
