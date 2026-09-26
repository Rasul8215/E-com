package com.e_com.project.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {

    private final String secretKey;
    private final long expirationTime;

    public JwtService(
            @Value("${jwt.secret}") String secretKey,
            @Value("${jwt.expiration-ms:3600000}") long expirationTime
    ) {
        this.secretKey = secretKey;
        this.expirationTime = expirationTime;
    }

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(
                secretKey.getBytes(StandardCharsets.UTF_8)
        );
    }

    public String generateToken(String username) {
        Date now = new Date();
        return Jwts
                .builder()
                .subject(username)
                .issuedAt(now)
                .expiration(new Date(now.getTime() + expirationTime))
                .signWith(getSigningKey())
                .claim("type", "ACCESS")
                .compact();
    }

    public String generateRefreshToken(String username) {
        Date now = new Date();
        return Jwts
                .builder()
                .subject(username)
                .issuedAt(now)
                .expiration(new Date(now.getTime() + expirationTime))
                .signWith(getSigningKey())
                .claim("type", "REFRESH")
                .compact();
    }


    public String extractnameToken(String token) {
        return Jwts
                .parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public String extractTokenType(String token) {
        return Jwts
                .parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("type", String.class);
    }



    public boolean isValidToke(String username, String token) {
        String name = extractnameToken(token);
        return username.equals(name) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        Date expiration = Jwts
                .parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration();
        return expiration.before(new Date());
    }

}
