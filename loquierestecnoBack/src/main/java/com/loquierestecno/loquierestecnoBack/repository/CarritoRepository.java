package com.loquierestecno.loquierestecnoBack.repository;

import com.loquierestecno.loquierestecnoBack.model.Carrito;
import com.loquierestecno.loquierestecnoBack.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarritoRepository extends JpaRepository<Carrito, Long> {
    Optional<Carrito> findByUsuarioId(Long usuario);
}