package com.loquierestecno.loquierestecnoBack.dto;

import com.loquierestecno.loquierestecnoBack.model.Pedido;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class RegistroPedidoDTO {

    private Long id;

    @NotNull(message = "La fecha del pedido es obligatoria")
    private Date fecha;

    @NotNull(message = "El estado del pedido es obligatorio")
    private Pedido.Estado estado;

    @NotNull(message = "El total es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El total debe ser mayor a 0")
    private Double total;

    @NotBlank(message = "La dirección es obligatoria")
    private String direccion;

    @NotBlank(message = "La ciudad es obligatoria")
    private String ciudad;

    @NotBlank(message = "El país es obligatorio")
    private String pais;

    @NotNull(message = "El usuario es obligatorio")
    private Long usuarioId;

    private List<RegistroDetallePedidoDTO> detalles;
}
