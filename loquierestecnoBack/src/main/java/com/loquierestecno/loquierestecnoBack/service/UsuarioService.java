package com.loquierestecno.loquierestecnoBack.service;

import com.loquierestecno.loquierestecnoBack.dto.RegistroUsuarioDTO;
import com.loquierestecno.loquierestecnoBack.exceptions.EmailAlreadyExistsException;
import com.loquierestecno.loquierestecnoBack.model.Usuario;
import com.loquierestecno.loquierestecnoBack.repository.UsuarioRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    //----- Metodo para crear un usuario rol cliente o administrador  -----\\
    @Transactional
    public Usuario registrarUsuario(RegistroUsuarioDTO dto) {
        if (usuarioRepository.existsByCorreo(dto.getCorreo())) {
            throw new EmailAlreadyExistsException("El correo ya está registrado.");
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(dto.getNombre());
        usuario.setApellido(dto.getApellido());
        usuario.setCorreo(dto.getCorreo());
        usuario.setContrasena(passwordEncoder.encode(dto.getContrasena()));
        usuario.setDireccion(dto.getDireccion());
        usuario.setTelefono(dto.getTelefono());
        usuario.setRol(dto.getRol());

        return usuarioRepository.save(usuario);
    }

    //----- METODO PARA CREAR UN USUARIO POR DEFECTO -----\\
    @PostConstruct
    public void crearPrimerAdministrador() {
        if (!usuarioRepository.existsByCorreo("andres.ok30@hotmail.com")) {
            Usuario administrador = new Usuario();
            administrador.setNombre("Andres");
            administrador.setApellido("Martinez");
            administrador.setCorreo("andres.ok30@hotmail.com");
            administrador.setContrasena("root12345");
            administrador.setDireccion("calle 49 1n155");
            administrador.setTelefono("3226327178");
            administrador.setRol(Usuario.Rol.ROLE_ADMINISTRADOR);

            usuarioRepository.save(administrador);
        }
    }
    public Usuario obtenerUsuarioPorCorreo(String correo){
        return usuarioRepository.findByCorreo(correo).get();
    }

    //-----

}