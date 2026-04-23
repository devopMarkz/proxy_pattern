package com.seguranca_proxy.security;

import com.seguranca_proxy.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService implements GenericUserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public GenericUser loadUserByUsername(String username) {
        return userRepository.findByLogin(username)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));
    }
}
