package com.seguranca_proxy.security.enums;

import lombok.Getter;

@Getter
public enum AccessEndpoint {
    AUTHENTICATED("authenticated"),
    DENIED_ALL("deniedAll"),
    PERMIT_ALL("permitAll");

    final String descricao;

    private AccessEndpoint(String descricao){
        this.descricao = descricao;
    }
}
