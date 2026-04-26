package com.seguranca_proxy.security;

public interface AuthenticationProvider {

    Authentication getAuthentication(String username, String password);

}
