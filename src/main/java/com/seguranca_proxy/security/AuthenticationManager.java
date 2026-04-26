package com.seguranca_proxy.security;

public interface AuthenticationManager {

    Authentication authenticate(String username, String password);

}
