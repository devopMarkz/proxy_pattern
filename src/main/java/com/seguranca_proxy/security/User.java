package com.seguranca_proxy.security;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "tb_user")
public class User implements GenericUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String login;

    private String senha;

    private Boolean ativo = Boolean.TRUE;

    @ManyToMany
    @JoinTable(
            name = "tb_role_user",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles = new ArrayList<>();

    public User() {
    }

    public User(String login, String senha, Boolean ativo) {
        this.login = login;
        this.senha = senha;
        this.ativo = ativo;
    }

    @Override
    public String getUsername() {
        return this.login;
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public List<GenericRole> getRoles() {
        return this.roles.stream().map(GenericRole.class::cast).toList();
    }

    @Override
    public Boolean isEnabled() {
        return this.ativo;
    }
}
