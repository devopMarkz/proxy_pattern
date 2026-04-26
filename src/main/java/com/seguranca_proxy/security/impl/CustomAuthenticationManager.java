package com.seguranca_proxy.security.impl;

import com.seguranca_proxy.security.Authentication;
import com.seguranca_proxy.security.AuthenticationManager;
import com.seguranca_proxy.security.AuthenticationProvider;

public class CustomAuthenticationManager implements AuthenticationManager {

    private AuthenticationProvider authenticationProvider;

    public CustomAuthenticationManager(AuthenticationProvider authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
    }

    @Override
    public Authentication authenticate(String username, String password) {
        return authenticationProvider.getAuthentication(username, password);
    }

}
