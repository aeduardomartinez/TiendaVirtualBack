package com.loquierestecno.loquierestecnoBack.controller;

import com.loquierestecno.loquierestecnoBack.dto.CrearPedidoRequest;
import com.loquierestecno.loquierestecnoBack.dto.PedidoDTO;
import com.loquierestecno.loquierestecnoBack.dto.RegistroUsuarioDTO;
import com.loquierestecno.loquierestecnoBack.model.Pedido;
import com.loquierestecno.loquierestecnoBack.model.Usuario;
import com.loquierestecno.loquierestecnoBack.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Data
@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {
    private final UsuarioService usuarioService;

    @PostMapping("/registro")
    public ResponseEntity registrarUsuario(@Valid @RequestBody RegistroUsuarioDTO dto) {
        usuarioService.registrarUsuario(dto);
        return ResponseEntity.ok(Map.of("mensaje", "Usuario registrado con éxito."));
    }

    // Editar un usuario existente
    @PutMapping("/editar/{id}")
    public ResponseEntity editarUsuario(@PathVariable Long id, @RequestBody RegistroUsuarioDTO dto) {
        Usuario usuarioActualizado = usuarioService.editarUsuario(id, dto);

        Map<String, Object> response = new HashMap<>();
        response.put("mensaje:","Usuario actualizado");
        return ResponseEntity.ok(response);
    }

    // Eliminar un usuario por ID
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.ok(Map.of("mensaje", "usuario eliminado"));
    }

}