package com.andrade.inventary_management_system_backend.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtUtil {

    private final long EXPIRE_TIME_IN_MILLSEC = 5L * 60L * 60L * 1000L; // expire in 5H
    private SecretKey key;

    @Value("${secret.key}")
    private String secretKey;

    @PostConstruct
    public void init() {
        byte[] bytes = secretKey.getBytes(StandardCharsets.UTF_8);
        this.key = new SecretKeySpec(bytes, "HmacSHA256");
    }

    public String encoder(String email) {
        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + EXPIRE_TIME_IN_MILLSEC))
                .signWith(key)
                .compact();
    }

    public String getUserNameFromToken(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimFunction) {
        return claimFunction.apply(
                Jwts.parser()
                        .verifyWith(key)
                        .build().parseSignedClaims(token)
                        .getPayload());

    }

    public boolean isValidToken(String token, UserDetails userDetails) {
        String userName = getUserNameFromToken(token);
        return userName.equals(userDetails.getUsername()) && isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
       return extractClaim(token, Claims::getExpiration).before(new Date());
    }

}
    