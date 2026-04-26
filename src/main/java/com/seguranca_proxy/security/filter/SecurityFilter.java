package com.seguranca_proxy.security.filter;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.seguranca_proxy.repository.UserRepository;
import com.seguranca_proxy.security.Authentication;
import com.seguranca_proxy.security.GenericUser;
import com.seguranca_proxy.security.SecurityContext;
import com.seguranca_proxy.security.TokenHelper;
import com.seguranca_proxy.security.impl.CustomAuthentication;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenHelper tokenHelper;
    private final SecurityContext securityContext;
    private final UserRepository userRepository;

    private final Logger logger = LoggerFactory.getLogger(SecurityFilter.class);

    public SecurityFilter(TokenHelper tokenHelper, SecurityContext securityContext, UserRepository userRepository) {
        this.tokenHelper = tokenHelper;
        this.securityContext = securityContext;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = tokenHelper.getBearerToken(request);

            DecodedJWT decoded = tokenHelper.tokenDecriptado(token);

            String username = decoded.getSubject();

            GenericUser user = userRepository.findByUsername(username)
                    .orElseThrow();

            Authentication auth = new CustomAuthentication(user, username, null);

            securityContext.setAuthentication(auth);

        } catch (Exception e) {
            logger.info("Erro ao salvar contexto.");
        }

        filterChain.doFilter(request, response);
    }

}
