package com.seguranca_proxy.security;

import com.seguranca_proxy.security.enums.AccessEndpoint;

import java.util.Objects;
import java.util.Set;

public class EndpointRule {

    private String method;
    private String uri;
    private Set<String> roles;
    private AccessEndpoint accessEndpoint;

    public EndpointRule(String method, String uri, Set<String> roles) {
        this.method = method;
        this.uri = uri;
        this.roles = roles;
    }

    public EndpointRule(String method, String uri, AccessEndpoint accessEndpoint) {
        this.method = method;
        this.uri = uri;
        this.accessEndpoint = accessEndpoint;
    }

    public String getUri() {
        return uri;
    }

    public String getMethod() {
        return method;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public AccessEndpoint getAccessEndpoint() {
        return accessEndpoint;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        EndpointRule that = (EndpointRule) o;
        return Objects.equals(method, that.method) && Objects.equals(uri, that.uri);
    }

    @Override
    public int hashCode() {
        return Objects.hash(method, uri);
    }
}
