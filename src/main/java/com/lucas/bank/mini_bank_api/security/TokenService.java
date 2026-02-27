package com.lucas.bank.mini_bank_api.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.lucas.bank.mini_bank_api.domain.entity.customer.CustomerDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(CustomerDetails customerDetails) {
        var algorithm = Algorithm.HMAC256(secret);

        return JWT.create()
                .withIssuer("mini-bank-api")
                .withSubject(customerDetails.getUsername()) // cpf
                .withExpiresAt(expiresAt())
                .sign(algorithm);
    }

    public String validateAndGetSubject(String token) {
        try {
            var algorithm = Algorithm.HMAC256(secret);

            return JWT.require(algorithm)
                    .withIssuer("mini-bank-api")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException e) {
            return null;
        }
    }


    private Instant expiresAt(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
