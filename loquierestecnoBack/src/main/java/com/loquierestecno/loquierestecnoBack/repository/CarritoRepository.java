package com.loquierestecno.loquierestecnoBack.repository;

import com.loquierestecno.loquierestecnoBack.model.Carrito;
import com.loquierestecno.loquierestecnoBack.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CarritoRepository extends JpaRepository<Carrito, Long> {
    Optional<Carrito> findByUsuarioId(Long usuario);

    @Query("SELECT c FROM Carrito c LEFT JOIN FETCH c.detalles WHERE c.usuario.id = :usuarioId")
    Optional<Carrito> findByUsuario(@Param("usuarioId") Long usuarioId);


}

