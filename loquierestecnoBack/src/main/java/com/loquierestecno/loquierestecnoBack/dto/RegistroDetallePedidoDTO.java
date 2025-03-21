package com.loquierestecno.loquierestecnoBack.dto;

import lombok.Data;

@Data
public class RegistroDetallePedidoDTO {
    private Long productoId;
    private String nombreProducto;
    private int cantidad;
    private double precioUnitario;
}
