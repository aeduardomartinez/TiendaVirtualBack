package com.loquierestecno.loquierestecnoBack.controller;

import com.loquierestecno.loquierestecnoBack.dto.AgregarCarritoRequest;
import com.loquierestecno.loquierestecnoBack.dto.CrearPedidoRequest;
import com.loquierestecno.loquierestecnoBack.dto.EliminarCarritoRequest;
import com.loquierestecno.loquierestecnoBack.dto.PedidoDTO;
import com.loquierestecno.loquierestecnoBack.model.Carrito;
import com.loquierestecno.loquierestecnoBack.model.Pedido;
import com.loquierestecno.loquierestecnoBack.service.CarritoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/carrito")
@RequiredArgsConstructor
public class CarritoController {

    private final CarritoService carritoService;

    @GetMapping("/{usuarioId}")
    public Carrito obtenerCarrito(@PathVariable Long usuarioId) {
        return carritoService.obtenerCarritoPorUsuario(usuarioId);
    }

    @PostMapping("/agregar")
    public ResponseEntity agregarProducto(@RequestBody AgregarCarritoRequest request) {
        carritoService.agregarProducto(request);
        return ResponseEntity.ok("producto agregado al carrito");
    }


    @DeleteMapping("/eliminar/")
    public ResponseEntity eliminarProducto(@RequestBody EliminarCarritoRequest dto) {
        carritoService.eliminarProducto(dto);
        return ResponseEntity.ok("producto eliminado del carrito");
    }

    @DeleteMapping("/{usuarioId}/vaciar")
    public void vaciarCarrito(@PathVariable Long usuarioId) {
        carritoService.vaciarCarrito(usuarioId);
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