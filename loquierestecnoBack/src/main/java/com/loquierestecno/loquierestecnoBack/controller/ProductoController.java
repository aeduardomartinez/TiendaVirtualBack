package com.loquierestecno.loquierestecnoBack.controller;

import com.loquierestecno.loquierestecnoBack.dto.RegistroProductoDTO;
import com.loquierestecno.loquierestecnoBack.model.Producto;
import com.loquierestecno.loquierestecnoBack.service.ProductoService;
import jakarta.validation.Valid;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Data
@RestController
@RequestMapping("/api/producto")
@CrossOrigin(origins = "*")
public class ProductoController {

    private final ProductoService productoService;

    @PostMapping("/registro")
    public ResponseEntity<String> registrarProducto(@Valid @RequestBody RegistroProductoDTO dto) {
        productoService.registrarProducto(dto);
        return ResponseEntity.ok("Producto registrado con éxito.");
    }

    // Actualizar producto
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Producto> actualizarProducto(
            @PathVariable Long id,
            @Valid @RequestBody RegistroProductoDTO dto) {
        return ResponseEntity.ok(productoService.actualizarProducto(id, dto));
    }
    // Listar todos los productos
    @GetMapping("/listar")
    public ResponseEntity<List<Producto>> listarProductos() {
        return ResponseEntity.ok(productoService.listarProductos());
    }

    // Eliminar producto por ID
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarProducto(@PathVariable Long id) {
        productoService.eliminarProducto(id);
        return ResponseEntity.ok("Producto eliminado con éxito.");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerProducto(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.obtenerProductoPorId(id));
    }
}
