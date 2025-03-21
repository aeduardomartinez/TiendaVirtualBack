package com.loquierestecno.loquierestecnoBack.controller;

import com.loquierestecno.loquierestecnoBack.dto.RegistroUsuarioDTO;
import com.loquierestecno.loquierestecnoBack.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Data
@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {
    private final UsuarioService usuarioService;

    @PostMapping("/registro")
    public ResponseEntity<String> registrarUsuario(@Valid @RequestBody RegistroUsuarioDTO dto) {
        usuarioService.registrarUsuario(dto);
        return ResponseEntity.ok("Usuario registrado con éxito.");
    }


}