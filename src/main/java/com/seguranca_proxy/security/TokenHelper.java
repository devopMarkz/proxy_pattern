package com.seguranca_proxy.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class TokenHelper {

    public DecodedJWT tokenDecriptado(String token) {
        Algorithm algorithm = Algorithm.HMAC256("secret");

        return JWT.require(algorithm)
                .withIssuer("api")
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

    public Set<String> getRoles(String token) {
        var decoded = tokenDecriptado(token);
        return new HashSet<>(decoded.getClaim("roles").asList(String.class));
    }

    public Map<String, Object> gerarToken(GenericUser user) {
        Algorithm algorithm = Algorithm.HMAC256("secret");

        String token = JWT.create()
                .withClaim("roles", List.of(user.getRoles().stream().map(GenericRole::getRoleName).toList()))
                .withSubject(user.getUsername())
                .withIssuer("api")
                .withIssuedAt(Instant.now())
                .withExpiresAt(Instant.now().plus(4, ChronoUnit.HOURS))
                .sign(algorithm);

        Map<String, Object> response = new HashMap<>();
        response.put("access_token", token);
        response.put("expires_at", tokenDecriptado(token).getExpiresAtAsInstant());

        return response;
    }

}
