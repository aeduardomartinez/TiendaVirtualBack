package com.loquierestecno.loquierestecnoBack.dto;

import lombok.Data;

@Data
public class CrearPedidoRequest {
    private Long usuarioId;
    private String direccion;
    private String ciudad;
    private String pais;
}