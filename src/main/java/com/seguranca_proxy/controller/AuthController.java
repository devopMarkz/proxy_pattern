package com.seguranca_proxy.controller;

import com.seguranca_proxy.controller.dto.UserCreateDTO;
import com.seguranca_proxy.security.annotations.HasRole;
import com.seguranca_proxy.security.filter.CustomSecurityEndpointsFilter;
import com.seguranca_proxy.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationService authenticationService;
    private final CustomSecurityEndpointsFilter securityEndpointsFilter;

    public AuthController(AuthenticationService authenticationService, CustomSecurityEndpointsFilter securityEndpointsFilter) {
        this.authenticationService = authenticationService;
        this.securityEndpointsFilter = securityEndpointsFilter;
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

    @GetMapping("/endpoint-sem-role")
    public String testeEndpointSemRole() {
        return "Endpoint sem role";
    }

    @GetMapping("/endpoint-com-role-user")
    @HasRole("ROLE_ADMIN")
    public String testeEndpointComRoleUser(HttpServletRequest request) {
        return "Endpoint com role user";
    }

}
