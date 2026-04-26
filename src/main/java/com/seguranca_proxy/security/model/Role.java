package com.seguranca_proxy.security.model;

import com.seguranca_proxy.security.GenericRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_role")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Role implements GenericRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String role;

    @ManyToMany(mappedBy = "roles")
    private List<User> users = new ArrayList<>();

    public Role(String role) {
        this.role = role;
    }

    @Override
    public String getRoleName() {
        return this.role;
    }

}
