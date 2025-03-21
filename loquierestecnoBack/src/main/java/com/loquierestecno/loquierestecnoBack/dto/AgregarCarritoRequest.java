package com.loquierestecno.loquierestecnoBack.dto;

import lombok.Data;

@Data
public class AgregarCarritoRequest {
    private Long usuarioId;
    private Long productoId;
    private int cantidad;
}