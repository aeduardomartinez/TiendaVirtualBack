package com.loquierestecno.loquierestecnoBack.service;

import com.loquierestecno.loquierestecnoBack.dto.RegistroProductoDTO;
import com.loquierestecno.loquierestecnoBack.model.Categoria;
import com.loquierestecno.loquierestecnoBack.model.Producto;
import com.loquierestecno.loquierestecnoBack.repository.CategoriaRepository;
import com.loquierestecno.loquierestecnoBack.repository.ProductoRepository;
import jakarta.transaction.Transactional;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;

@Data
@Service
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    //Metodo para poder crear un producto
    @Transactional
    public Producto registrarProducto(RegistroProductoDTO dto) {

        Producto producto = new Producto();
        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setPrecio(dto.getPrecio());
        producto.setImagen(dto.getImagen());
        producto.setStock(dto.getStock());

        //Realiza una consulta para ver si existe la categoria en la BD
        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId()).orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        producto.setCategoria(categoria);
        return productoRepository.save(producto);
    }

    @Transactional
    public Producto actualizarProducto(Long id, RegistroProductoDTO dto) {
        Producto producto = productoRepository.findById(id).orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId()).orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setPrecio(dto.getPrecio());
        producto.setImagen(dto.getImagen());
        producto.setStock(dto.getStock());
        producto.setCategoria(categoria);

        return productoRepository.save(producto);
    }

    public List<Producto> listarProductos() {
        return productoRepository.findAll();
    }


    public void eliminarProducto(Long id) {
        if (!productoRepository.existsById(id)) {
            throw new RuntimeException("Producto no encontrado");
        }
        productoRepository.deleteById(id);
    }

    // Obtener producto por ID
    public Producto obtenerProductoPorId(Long id) {
        Producto producto = productoRepository.findById(id).orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        return producto;
    }
}
