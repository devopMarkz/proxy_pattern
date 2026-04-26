package com.seguranca_proxy.security;

import org.springframework.stereotype.Component;

@Component
public class SecurityContext {

    private Authentication authentication;

    public void setAuthentication(Authentication authentication) {
        this.authentication = authentication;
    }

    public Authentication getAuthentication() {
        return authentication;
    }

}
