package com.seguranca_proxy.security.impl;

import com.seguranca_proxy.security.Authentication;
import com.seguranca_proxy.security.GenericRole;
import com.seguranca_proxy.security.GenericUser;

import java.util.ArrayList;
import java.util.List;

public class CustomAuthentication implements Authentication {

    private GenericUser genericUser;
    private final String username;
    private final String password;
    private List<GenericRole> roles = new ArrayList<>();

    public CustomAuthentication(GenericUser genericUser, String username, String password) {
        this.genericUser = genericUser;
        this.username = username;
        this.password = password;
    }

    public CustomAuthentication(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public CustomAuthentication(GenericUser genericUser, String username, String password, List<GenericRole> roles) {
        this.genericUser = genericUser;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    @Override
    public GenericUser getGenericUser() {
        return genericUser;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public List<GenericRole> getRoles() {
        return roles;
    }
}
