package com.seguranca_proxy.security;

import com.seguranca_proxy.security.enums.AccessEndpoint;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class CustomSecurityFilter extends OncePerRequestFilter {

    private Set<EndpointRule> endpointRules = new HashSet<>();
    private AccessEndpoint otherEndpoints;

    private final TokenHelper tokenHelper;
    private final GenericUserService genericUserService;

    public CustomSecurityFilter(TokenHelper tokenHelper, GenericUserService genericUserService) {
        this.tokenHelper = tokenHelper;
        this.genericUserService = genericUserService;
    }

    public CustomSecurityFilter requestMatchers(String method, String uri, String... roles) {
        endpointRules.add(new EndpointRule(method, uri, Set.of(roles)));
        return this;
    }

    public CustomSecurityFilter requestMatchers(String method, String uri, AccessEndpoint access) {
        endpointRules.add(new EndpointRule(method, uri, access));
        return this;
    }

    public CustomSecurityFilter otherEndpoints(AccessEndpoint accessEndpoint) {
        this.otherEndpoints = accessEndpoint;
        return this;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

    }

}
