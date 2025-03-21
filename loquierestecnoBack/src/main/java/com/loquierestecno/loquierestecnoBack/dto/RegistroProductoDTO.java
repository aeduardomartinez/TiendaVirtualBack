package com.loquierestecno.loquierestecnoBack.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RegistroProductoDTO {
    @NotBlank(message = "El nombre del producto es obligatorio")
    @Size(max = 100, message = "El nombre no puede tener más de 100 caracteres")
    @Column(name = "nombre")
    private String nombre;

    @NotBlank(message = "La descripción es obligatoria")
    @Column(name = "descripcion")
    private String descripcion;

    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor a 0")
    @Column(name = "precio")
    private Double precio;

    private String imagen;

    @NotNull(message = "El stock es obligatorio")
    @Min(value = 0, message = "El stock no puede ser negativo")
    @Column(name = "stock")
    private Integer stock;

    @NotNull(message = "La categoría es obligatoria")
    private Long categoriaId;
}
