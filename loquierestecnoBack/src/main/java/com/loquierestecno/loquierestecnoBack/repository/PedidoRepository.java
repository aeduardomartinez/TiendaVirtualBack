package com.loquierestecno.loquierestecnoBack.repository;

import com.loquierestecno.loquierestecnoBack.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    Optional<Pedido> findById(Long id);
}
