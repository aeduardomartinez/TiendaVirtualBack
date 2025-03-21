package com.loquierestecno.loquierestecnoBack.repository;

import com.loquierestecno.loquierestecnoBack.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    Optional<Producto> findById(Long idCategoria);

    List<Producto> findAll();
}
