package com.seguranca_proxy.security;

import java.util.List;

public interface Authentication {

    GenericUser getGenericUser();

    String getUsername();

    String getPassword();

    List<GenericRole> getRoles();

}
