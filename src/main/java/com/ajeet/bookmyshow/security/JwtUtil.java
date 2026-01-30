package com.ajeet.bookmyshow.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration-ms}")
    private Long expirationMs;

    public String generateToken(String subject) {
        Algorithm alg = Algorithm.HMAC256(jwtSecret.getBytes());
        return JWT.create()
                .withSubject(subject)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + expirationMs))
                .sign(alg);
    }

    public String extractSubject(String token) {
        Algorithm alg = Algorithm.HMAC256(jwtSecret.getBytes());
        DecodedJWT jwt = JWT.require(alg).build().verify(token);
        return jwt.getSubject();
    }
}
