package com.example.productcatalog.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    // Injected from application.properties
    @Value("${jwt.secret-key}")
    private String secret;
    
    @Value("${jwt.expiration-ms}")
    private long expirationMs;

    private Key getSigningKey() {
        // Validate secret length (minimum 256 bits/32 chars for HS256)
        if (secret == null || secret.length() < 32) {
            throw new IllegalStateException("JWT secret must be at least 32 characters long");
        }
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateToken(UserDetails userDetails) {  
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
            .claim("roles", userDetails.getAuthorities().stream()
                    .map(auth -> auth.getAuthority())
                    .toList()
            )
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) throws JwtException {
        return parseToken(token).getBody().getSubject();
    }

    public boolean validateToken(String token) {
    try {
        parseToken(token);
        return true;
    } catch (SignatureException e) {
        System.out.println("Invalid JWT signature: " + e.getMessage());
        return false;
    } catch (ExpiredJwtException e) {
        System.out.println("Expired JWT token: " + e.getMessage());
        return false;
    } catch (JwtException | IllegalArgumentException e) {
        System.out.println("Invalid JWT token: " + e.getMessage());
        return false;
    }
}


    private Jws<Claims> parseToken(String token) throws JwtException {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token);
    }

    public boolean isTokenValid(String token, String username) {
        try {
            final String extractedUsername = extractUsername(token);
            return extractedUsername.equals(username) && !isTokenExpired(token);
        } catch (JwtException e) {
            return false;
        }
    }

    private boolean isTokenExpired(String token) throws ExpiredJwtException {
        try {
            parseToken(token);
            return false;
        } catch (ExpiredJwtException e) {
            return true;
        }
    }
}