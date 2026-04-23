package com.seguranca_proxy.security;

import java.util.List;

public interface GenericUser {

    String getUsername();

    String getPassword();

    List<GenericRole> getRoles();

    Boolean isEnabled();

}
