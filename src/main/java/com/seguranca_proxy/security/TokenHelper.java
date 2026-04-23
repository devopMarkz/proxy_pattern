package com.seguranca_proxy.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

@Service
public class TokenHelper {

    public DecodedJWT tokenDecriptado(String token) {
        Algorithm algorithm = Algorithm.HMAC256("secret");

        return JWT.require(algorithm)
                .withIssuer("x")
                .build()
                .verify(token);
    }

    public String getBearerToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if(bearerToken == null || !bearerToken.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Token ilegal.");
        }

        return bearerToken.split(" ")[1];
    }

}
