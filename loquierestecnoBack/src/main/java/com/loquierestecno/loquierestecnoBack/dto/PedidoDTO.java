package com.loquierestecno.loquierestecnoBack.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class PedidoDTO {
    private Long id;
    private Date fecha;
    private String estado;
    private Double total;
    private String direccion;
    private String ciudad;
    private String pais;
    private List<RegistroDetallePedidoDTO> detalles;
}