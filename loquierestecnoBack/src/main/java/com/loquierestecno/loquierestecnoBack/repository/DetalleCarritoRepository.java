package com.loquierestecno.loquierestecnoBack.repository;

import com.loquierestecno.loquierestecnoBack.model.DetalleCarrito;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DetalleCarritoRepository extends JpaRepository<DetalleCarrito, Long> {

    Optional<DetalleCarrito> findByCarritoIdAndProductoId(Long carritoId, Long productoId);
     void deleteByCarritoId(Long carritoId);
}