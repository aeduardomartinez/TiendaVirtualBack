package com.loquierestecno.loquierestecnoBack.dto.security;

import lombok.Builder;
import lombok.Data;

/**
 * Esta clase es usada para el REST de obtener informacion del usuario al momento de autorizacion
 *
 * @author Andres.Martinez
 *
 */
@Data
@Builder
public class UsuarioAuthorizationDto {
    String username;
    String rol;
}
