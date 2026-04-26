package com.seguranca_proxy.config;

import com.seguranca_proxy.security.*;
import com.seguranca_proxy.security.enums.AccessEndpoint;
import com.seguranca_proxy.security.filter.CustomSecurityEndpointsFilter;
import com.seguranca_proxy.security.filter.SecurityFilter;
import com.seguranca_proxy.security.impl.CustomAuthenticationManager;
import com.seguranca_proxy.security.impl.UserService;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConfig {

    @Bean
    public CustomSecurityEndpointsFilter securityEndpointsFilter(TokenHelper tokenHelper, UserService userService) {
        return new CustomSecurityEndpointsFilter(tokenHelper, userService)
                .requestMatchers("POST", "/auth/login", AccessEndpoint.PERMIT_ALL)
                .requestMatchers("POST", "/auth/register", AccessEndpoint.PERMIT_ALL)
                .requestMatchers("GET", "/auth/endpoint-com-role-user", "ROLE_USER")
                .otherEndpoints(AccessEndpoint.AUTHENTICATED);
    }

    @Bean
    public AuthenticationManager getAuthenticationManager(AuthenticationProvider authenticationProvider) {
        return new CustomAuthenticationManager(authenticationProvider);
    }

    @Bean
    public FilterRegistrationBean<SecurityFilter> securityFilterRegistration(SecurityFilter filter) {
        FilterRegistrationBean<SecurityFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(filter);
        registration.setOrder(1);
        return registration;
    }

    @Bean
    public FilterRegistrationBean<CustomSecurityEndpointsFilter> endpointsFilterRegistration(CustomSecurityEndpointsFilter filter) {
        FilterRegistrationBean<CustomSecurityEndpointsFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(filter);
        registration.setOrder(2);
        return registration;
    }

}
