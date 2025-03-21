package com.loquierestecno.loquierestecnoBack.security.service;

import com.loquierestecno.loquierestecnoBack.dto.security.AuthRequest;
import com.loquierestecno.loquierestecnoBack.dto.security.AuthResponse;
import com.loquierestecno.loquierestecnoBack.dto.security.UsuarioAuthorizationDto;

public interface IAuthenticationService {
    AuthResponse login(AuthRequest request);
    UsuarioAuthorizationDto infoUsuario(String username);
}