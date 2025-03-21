package com.loquierestecno.loquierestecnoBack.security.service.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import com.loquierestecno.loquierestecnoBack.dto.security.AuthRequest;
import com.loquierestecno.loquierestecnoBack.dto.security.AuthResponse;
import com.loquierestecno.loquierestecnoBack.dto.security.UsuarioAuthorizationDto;
import com.loquierestecno.loquierestecnoBack.model.Usuario;
import com.loquierestecno.loquierestecnoBack.security.service.IAuthenticationService;
import com.loquierestecno.loquierestecnoBack.security.service.IJwtService;
import com.loquierestecno.loquierestecnoBack.service.UsuarioService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements IAuthenticationService {

    private final AuthenticationManager  authenticationManager;
    private final UsuarioService usuarioService;
    private final IJwtService tokenProvider;

    @Override
    public AuthResponse login(AuthRequest request) {

        // Aqui se llama a @Bean public AuthenticationProvider en securityConfiguration. En este caso es MphUserDetailService
        Authentication au =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        String jwt = tokenProvider.createToken(au);

        return AuthResponse.builder().token(jwt).build();
    }

    // =============================================================================================

    @Override
    public UsuarioAuthorizationDto infoUsuario(String email) {
        Usuario col = usuarioService.obtenerUsuarioPorCorreo(email);
        UsuarioAuthorizationDto usuarioResp = UsuarioAuthorizationDto.builder()
                .username(email)
                .rol(col.getRol().name())
                .build();
        return usuarioResp;
    }



    // =============================================================================================

}
