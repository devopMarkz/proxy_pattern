package com.seguranca_proxy.security.filter;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.seguranca_proxy.security.GenericUserService;
import com.seguranca_proxy.security.TokenHelper;
import com.seguranca_proxy.security.enums.AccessEndpoint;
import com.seguranca_proxy.security.model.EndpointRule;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CustomSecurityEndpointsFilter extends OncePerRequestFilter {

    private List<EndpointRule> endpointRules = new ArrayList<>();
    private AccessEndpoint otherEndpoints;
    private AntPathMatcher matcher = new AntPathMatcher();

    private final TokenHelper tokenHelper;
    private final GenericUserService genericUserService;

    public CustomSecurityEndpointsFilter(TokenHelper tokenHelper, GenericUserService genericUserService) {
        this.tokenHelper = tokenHelper;
        this.genericUserService = genericUserService;
    }

    public CustomSecurityEndpointsFilter requestMatchers(String method, String uri, String... roles) {
        endpointRules.add(new EndpointRule(method, uri, Set.of(roles)));
        return this;
    }

    public CustomSecurityEndpointsFilter requestMatchers(String method, String uri, AccessEndpoint access) {
        endpointRules.add(new EndpointRule(method, uri, access));
        return this;
    }

    public CustomSecurityEndpointsFilter otherEndpoints(AccessEndpoint accessEndpoint) {
        this.otherEndpoints = accessEndpoint;
        return this;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String uri = request.getRequestURI();
        String method = request.getMethod();

        for(EndpointRule rule : endpointRules) {

            boolean uriMatches = match(rule.getUri(), uri);
            boolean methodMatches = rule.getMethod().equalsIgnoreCase(method);

            if(uriMatches && methodMatches) {
                if (rule.getAccessEndpoint() == AccessEndpoint.PERMIT_ALL) {
                    filterChain.doFilter(request, response);
                    return;
                }

                if (rule.getAccessEndpoint() == AccessEndpoint.DENIED_ALL) {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    return;
                }

                String token = tokenHelper.getBearerToken(request);

                if (token == null) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }

                DecodedJWT decoded;

                try {
                    decoded = tokenHelper.tokenDecriptado(token);
                } catch (Exception e) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }

                if (rule.getAccessEndpoint() == AccessEndpoint.AUTHENTICATED) {
                    filterChain.doFilter(request, response);
                    return;
                }

                List<String> rolesClaim = decoded.getClaim("roles").asList(String.class);

                Set<String> userRoles = rolesClaim != null
                        ? new HashSet<>(rolesClaim)
                        : new HashSet<>();

                boolean autorizado = userRoles.stream()
                        .anyMatch(rule.getRoles()::contains);

                if (!autorizado) {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    return;
                }

                filterChain.doFilter(request, response);
                return;
            }

        }

        if (otherEndpoints == AccessEndpoint.PERMIT_ALL) {
            filterChain.doFilter(request, response);
            return;
        }

        if (otherEndpoints == AccessEndpoint.DENIED_ALL) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        String token = tokenHelper.getBearerToken(request);

        if (token == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        try {
            tokenHelper.tokenDecriptado(token);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        filterChain.doFilter(request, response);

    }

    private boolean match(String pattern, String path) {
        return matcher.match(pattern, path);
    }

}
