package com.seguranca_proxy.security.impl;

import com.seguranca_proxy.security.Authentication;
import com.seguranca_proxy.security.AuthenticationProvider;
import com.seguranca_proxy.security.GenericUser;
import com.seguranca_proxy.security.GenericUserService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final GenericUserService genericUserService;

    public CustomAuthenticationProvider(GenericUserService genericUserService) {
        this.genericUserService = genericUserService;
    }

    @Override
    @Transactional
    public Authentication getAuthentication(String username, String password) {
        GenericUser user = genericUserService.loadUserByUsername(username);

        if(!user.getUsername().equals(username) || !user.getPassword().equals(password)) {
            throw new IllegalArgumentException("Usuário inválido.");
        }

        return new CustomAuthentication(user, username, password, user.getRoles());
    }

}
