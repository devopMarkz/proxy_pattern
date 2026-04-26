package com.seguranca_proxy.security.impl;

import com.seguranca_proxy.repository.UserRepository;
import com.seguranca_proxy.security.GenericUser;
import com.seguranca_proxy.security.GenericUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService implements GenericUserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public GenericUser loadUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));
    }
}
