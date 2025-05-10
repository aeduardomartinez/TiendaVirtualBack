package com.loquierestecno.loquierestecnoBack.controller;

import com.loquierestecno.loquierestecnoBack.dto.*;
import com.loquierestecno.loquierestecnoBack.model.Carrito;
import com.loquierestecno.loquierestecnoBack.model.Pedido;
import com.loquierestecno.loquierestecnoBack.service.CarritoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/carrito")
@RequiredArgsConstructor
public class CarritoController {

    private final CarritoService carritoService;

    @GetMapping("total-productoscar/{usuarioId}")
    public ResponseEntity<List<MostrarDetalleCarritoDTO>> obtenerProductosCarrito(@PathVariable Long usuarioId) {
        List<MostrarDetalleCarritoDTO> detalles = carritoService.obtenerDetallesCarrito(usuarioId);

        if (detalles.isEmpty()) {
            return ResponseEntity.noContent().build(); // HTTP 204 si el carrito está vacío
        }

        return ResponseEntity.ok(detalles); // HTTP 200 con los productos

    }

    @PostMapping("/agregar")
    public ResponseEntity agregarProducto(@RequestBody AgregarCarritoRequest request) {
        carritoService.agregarProducto(request);
        return ResponseEntity.ok(Map.of("mensaje","producto agregado al carrito"));
    }


    @DeleteMapping("/eliminar/")
    public ResponseEntity eliminarProducto(@RequestBody EliminarCarritoRequest dto) {
        carritoService.eliminarProductoDelCarrito(dto);
        return ResponseEntity.ok(Map.of("mensaje","✅ Producto eliminado del carrito"));
    }

    @DeleteMapping("/{usuarioId}/vaciar")
    public ResponseEntity vaciarCarrito(@PathVariable Long usuarioId) {
        carritoService.vaciarCarrito(usuarioId);
        return ResponseEntity.ok(Map.of("mensaje", "Carrito vaciado con éxito."));
    }


    @PostMapping("/crear-pedido")
    public ResponseEntity crearPedido(@RequestBody CrearPedidoRequest request) {
        Pedido pedido = carritoService.crearPedido(request);

        PedidoDTO pedidoDTO = carritoService.convertirAPedidoDTO(pedido);

        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "✅ Pedido creado exitosamente");
        response.put("pedido", pedidoDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


}