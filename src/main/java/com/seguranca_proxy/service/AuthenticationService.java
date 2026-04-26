package com.seguranca_proxy.service;

import com.seguranca_proxy.controller.dto.UserCreateDTO;
import com.seguranca_proxy.repository.RoleRepository;
import com.seguranca_proxy.repository.UserRepository;
import com.seguranca_proxy.security.*;
import com.seguranca_proxy.security.model.User;
import com.seguranca_proxy.security.model.Role;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final TokenHelper tokenHelper;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository userRepository, RoleRepository roleRepository, TokenHelper tokenHelper, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.tokenHelper = tokenHelper;
        this.authenticationManager = authenticationManager;
    }

    @Transactional
    public void cadastrarUsuario(UserCreateDTO dto){
        User user = new User(dto.login(), dto.senha(), Boolean.TRUE);

        Role role = roleRepository.findByRole("ROLE_USER")
                .orElseThrow(() -> new IllegalArgumentException("Role inexistente."));

        user.getRoles().add(role);

        userRepository.save(user);
    }

    public Map<String, Object> login(UserCreateDTO dto) {
        Authentication authentication = authenticationManager.authenticate(dto.login(), dto.senha());
        return tokenHelper.gerarToken(authentication.getGenericUser());
    }

}
