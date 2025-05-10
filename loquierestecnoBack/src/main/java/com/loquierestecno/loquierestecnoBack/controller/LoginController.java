package com.loquierestecno.loquierestecnoBack.controller;

import com.loquierestecno.loquierestecnoBack.dto.security.AuthRequest;
import com.loquierestecno.loquierestecnoBack.dto.security.AuthResponse;
import com.loquierestecno.loquierestecnoBack.security.service.IAuthenticationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/usuarios")
@AllArgsConstructor
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final IAuthenticationService authenticationService;


    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getCorreo(), loginRequest.getContrasena()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return ResponseEntity.ok(Map.of("mensaje","Login exitoso"));
    }

    @PostMapping("/authenticate")
    private ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest authRequest) {
        return ResponseEntity.ok(authenticationService.login(authRequest));
    }
    @Data
    public static class LoginRequest {
        private String correo;
        private String contrasena;

    }
}