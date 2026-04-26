package com.seguranca_proxy.config;

import com.seguranca_proxy.security.*;
import com.seguranca_proxy.security.enums.AccessEndpoint;
import com.seguranca_proxy.security.filter.CustomSecurityEndpointsFilter;
import com.seguranca_proxy.security.impl.CustomAuthenticationManager;
import com.seguranca_proxy.security.impl.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConfig {

    @Bean
    public CustomSecurityEndpointsFilter securityEndpointsFilter(TokenHelper tokenHelper, UserService userService) {
        return new CustomSecurityEndpointsFilter(tokenHelper, userService)
                .requestMatchers("POST", "/auth/login", AccessEndpoint.PERMIT_ALL)
                .requestMatchers("POST", "/auth/register", AccessEndpoint.PERMIT_ALL)
                .otherEndpoints(AccessEndpoint.AUTHENTICATED);
    }

    @Bean
    public AuthenticationManager getAuthenticationManager(AuthenticationProvider authenticationProvider) {
        return new CustomAuthenticationManager(authenticationProvider);
    }

}
