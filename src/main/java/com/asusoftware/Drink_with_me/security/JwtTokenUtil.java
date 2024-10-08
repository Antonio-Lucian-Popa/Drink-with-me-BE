package com.asusoftware.Drink_with_me.security;

import com.asusoftware.Drink_with_me.security.exception.JwtTokenGenerationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

@Component
public class JwtTokenUtil {

    @Value("${application.security.jwt.secret}")
    private String secret;

    @Value("${application.security.jwt.expiration-time}")
    private long expirationTime;


    public String generateToken(UserDetails userDetails, UUID userId, Map<String, Object> extraClaims) {
        try {
            // Create a SecretKey object from the secret
            SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());

            return Jwts.builder()
                    .setSubject(userDetails.getUsername())
                    //.setClaims(extraClaims)
                    .claim("userId", userId) // Add the userId as a custom claim
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                    .signWith(key, SignatureAlgorithm.HS512)  // Use the key and algorithm
                    .compact();
        } catch (Exception e) {
            // Log the error for debugging purposes
            e.printStackTrace();

            // Throw a custom exception or a generic RuntimeException
            throw new JwtTokenGenerationException("Failed to generate JWT token", e);
        }
    }

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return getExpirationDateFromToken(token).before(new Date());
    }

    private Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


        // Encodes the secret key in Base64
    private String encodeSecret(String secret) {
        return Base64.getEncoder().encodeToString(secret.getBytes());
    }
}
