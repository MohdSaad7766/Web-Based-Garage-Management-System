package com.aziz_motors.Web_Based.Garage.Management.System.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

import java.util.Date;
import java.util.UUID;

@Service
@Slf4j
public class JwtService {

    private final SecretKey key;
    private final long expiration;
    private final String issuer;

    public JwtService(@Value("${jwt.secret.code}") String secretCode,
                      @Value("${jwt.expiration.ms}")long expiration,
                      @Value("${jwt.issuer}")String issuer ){

        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretCode));
        this.expiration = expiration;
        this.issuer = issuer;
    }

    public String generateToken(String email){
        return Jwts.builder()
                .issuer(issuer)
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .id(UUID.randomUUID().toString())
                .signWith(key)
                .compact();
    }

    public String extractEmail(String token){
        return extractClaims(token).getSubject();
    }

    public Claims extractClaims(String token){
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
    }
    public boolean isTokenValid(String token, UserDetails userDetails){
        try {
            Claims claims = extractClaims(token);

            return claims.getSubject().equals(userDetails.getUsername())
                    && claims.getIssuer().equals(issuer)
                    && claims.getExpiration().after(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            log.warn("JWT validation failed: {}", e.getMessage());

            return false;
        }
    }
}
