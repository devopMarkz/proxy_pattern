package com.seguranca_proxy.security.model;

import com.seguranca_proxy.security.GenericRole;
import com.seguranca_proxy.security.GenericUser;
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

    private String username;

    private String senha;

    private Boolean ativo = Boolean.TRUE;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "tb_role_user",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles = new ArrayList<>();

    public User() {
    }

    public User(String username, String senha, Boolean ativo) {
        this.username = username;
        this.senha = senha;
        this.ativo = ativo;
    }

    @Override
    public String getUsername() {
        return this.username;
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
