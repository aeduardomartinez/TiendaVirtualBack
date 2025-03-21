package com.loquierestecno.loquierestecnoBack.service;

import com.loquierestecno.loquierestecnoBack.dto.*;
import com.loquierestecno.loquierestecnoBack.model.*;
import com.loquierestecno.loquierestecnoBack.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CarritoService {

    private final CarritoRepository carritoRepository;
    private final DetalleCarritoRepository detalleCarritoRepository;
    private final ProductoRepository productoRepository;
    private final UsuarioRepository usuarioRepository;
    private final PedidoRepository pedidoRepository;

    // Obtener el carrito de un usuario
    public Carrito obtenerCarritoPorUsuario(Long usuarioId) {
        return carritoRepository.findByUsuarioId(usuarioId)
                .orElseGet(() -> {
                    Usuario usuario = usuarioRepository.findById(usuarioId)
                            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
                    Carrito nuevoCarrito = new Carrito();
                    nuevoCarrito.setUsuario(usuario);
                    return carritoRepository.save(nuevoCarrito);
                });
    }

    // Agregar un producto al carrito
    @Transactional
    public Carrito agregarProducto(AgregarCarritoRequest dto) {

        // Obtener el carrito del usuario
        Carrito carrito = obtenerCarritoPorUsuario(dto.getUsuarioId());
        if (carrito == null) {
            throw new RuntimeException("Carrito no encontrado para el usuario con ID: " + dto.getUsuarioId());
        }
        System.out.println("✅ Carrito encontrado: " + carrito.getId());

        // Buscar el producto
        Producto producto = productoRepository.findById(dto.getProductoId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + dto.getProductoId()));
        System.out.println("✅ Producto encontrado: " + producto.getNombre());

        // Asegurar que la lista de detalles no sea null
        if (carrito.getDetalles() == null) {
            carrito.setDetalles(new ArrayList<>());
        }
        // Verificar si el producto ya está en el carrito
        Optional<DetalleCarrito> detalleExistente = carrito.getDetalles().stream()
                .filter(detalle -> detalle.getProducto().getId().equals(dto.getProductoId()))
                .findFirst();

        if (detalleExistente.isPresent()) {
            // Si ya existe, actualizar cantidad
            DetalleCarrito detalle = detalleExistente.get();
            detalle.setCantidad(detalle.getCantidad() + dto.getCantidad());
            detalleCarritoRepository.save(detalle);
        } else {
            // Si no existe, agregar un nuevo detalle
            DetalleCarrito nuevoDetalle = new DetalleCarrito();
            nuevoDetalle.setCarrito(carrito);
            nuevoDetalle.setProducto(producto);
            nuevoDetalle.setCantidad(dto.getCantidad());
            detalleCarritoRepository.save(nuevoDetalle);
            carrito.getDetalles().add(nuevoDetalle);
        }

        return carritoRepository.save(carrito);
    }
    @Transactional
    public String eliminarProducto(EliminarCarritoRequest dto) {
        Carrito carrito = obtenerCarritoPorUsuario(dto.getUsuarioId());

        Optional<DetalleCarrito> detalleExistente = carrito.getDetalles().stream()
                .filter(detalle -> detalle.getProducto().getId().equals(dto.getProductoId()))
                .findFirst();

        if (detalleExistente.isPresent()) {
            DetalleCarrito detalle = detalleExistente.get();

            // 🔥 ELIMINAR PRIMERO DE LA BD 🔥
            detalleCarritoRepository.deleteById(detalle.getId());
            carritoRepository.deleteById(carrito.getId());
            detalleCarritoRepository.flush();

            // 🧹 ELIMINAR DE LA LISTA EN MEMORIA
            carrito.getDetalles().remove(detalle);

            return "✅ Producto eliminado del carrito y de la base de datos";
        } else {
            return "⚠️ Producto no encontrado en el carrito";
        }
    }



    // Vaciar el carrito
    @Transactional
    public void vaciarCarrito(Long usuarioId) {
        Carrito carrito = obtenerCarritoPorUsuario(usuarioId);
        detalleCarritoRepository.deleteAll(carrito.getDetalles());
        carritoRepository.deleteById(carrito.getId());
        carrito.getDetalles().clear();
        carritoRepository.save(carrito);
    }
    @Transactional
    public Pedido crearPedido(CrearPedidoRequest dto) {
        // 🔍 1️⃣ Obtener el carrito del usuario
        Carrito carrito = carritoRepository.findByUsuarioId(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));

        if (carrito.getDetalles().isEmpty()) {
            throw new RuntimeException("❌ No puedes crear un pedido con un carrito vacío.");
        }

        // 📝 2️⃣ Crear el pedido
        Pedido nuevoPedido = new Pedido();
        nuevoPedido.setUsuario(carrito.getUsuario());
        nuevoPedido.setFecha(new Date());
        nuevoPedido.setEstado(Pedido.Estado.PENDIENTE);
        nuevoPedido.setDireccion(dto.getDireccion());
        nuevoPedido.setCiudad(dto.getCiudad());
        nuevoPedido.setPais(dto.getPais());

        // 🛒 3️⃣ Convertir los detalles del carrito en detalles de pedido
        List<DetallePedido> detallesPedido = carrito.getDetalles().stream().map(detalle -> {
            DetallePedido detallePedido = new DetallePedido();
            detallePedido.setPedido(nuevoPedido);
            detallePedido.setProducto(detalle.getProducto());
            detallePedido.setCantidad(detalle.getCantidad());
            detallePedido.setPrecioUnitario(detalle.getProducto().getPrecio());
            return detallePedido;
        }).toList();

        nuevoPedido.setDetalles(detallesPedido);

        // 🏷 4️⃣ Calcular total
        double totalPedido = detallesPedido.stream()
                .mapToDouble(d -> d.getCantidad() * d.getPrecioUnitario())
                .sum();
        nuevoPedido.setTotal(totalPedido);

        // 💾 5️⃣ Guardar el pedido y los detalles
        pedidoRepository.save(nuevoPedido);

        // 🧹 6️⃣ Vaciar el carrito
 //       vaciarCarrito(dto.getUsuarioId());

        return nuevoPedido;
    }

    public PedidoDTO convertirAPedidoDTO(Pedido pedido) {
        PedidoDTO dto = new PedidoDTO();
        dto.setId(pedido.getId());
        dto.setFecha(pedido.getFecha());
        dto.setEstado(pedido.getEstado().toString());
        dto.setTotal(pedido.getTotal());
        dto.setDireccion(pedido.getDireccion());
        dto.setCiudad(pedido.getCiudad());
        dto.setPais(pedido.getPais());

        List<RegistroDetallePedidoDTO> detalles = pedido.getDetalles().stream().map(detalle -> {
            RegistroDetallePedidoDTO detDto = new RegistroDetallePedidoDTO();
            detDto.setProductoId(detalle.getProducto().getId());
            detDto.setNombreProducto(detalle.getProducto().getNombre());
            detDto.setCantidad(detalle.getCantidad());
            detDto.setPrecioUnitario(detalle.getProducto().getPrecio());
            return detDto;
        }).toList();

        dto.setDetalles(detalles);
        return dto;
    }


}