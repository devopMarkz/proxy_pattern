package com.seguranca_proxy.controller;

import com.seguranca_proxy.controller.dto.UserCreateDTO;
import com.seguranca_proxy.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationService authenticationService;

    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> cadastrarUsuario(@RequestBody UserCreateDTO dto){
        authenticationService.cadastrarUsuario(dto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody UserCreateDTO dto) {
        Map<String, Object> response = authenticationService.login(dto);
        return ResponseEntity.ok(response);
    }

}
