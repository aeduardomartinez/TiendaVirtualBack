package com.loquierestecno.loquierestecnoBack.dto;
import lombok.Data;

@Data
public class EliminarCarritoRequest {
    private Long usuarioId;
    private Long productoId;
}
