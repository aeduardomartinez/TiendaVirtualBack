package com.loquierestecno.loquierestecnoBack.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "pedido")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull(message = "La fecha del pedido es obligatoria")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha = new Date();

    @NotNull(message = "El estado del pedido es obligatorio")
    @Enumerated(EnumType.STRING)
    private Estado estado = Estado.PENDIENTE;

    @NotNull(message = "El total es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El total debe ser mayor a 0")
    @Column(name = "total")
    private Double total = 0.0;

    @NotBlank(message = "La direccion es obligatorio")
    @Column(name = "direccion")
    private String direccion;

    @NotBlank(message = "La ciudad es obligatorio")
    @Column(name = "ciudad")
    private String ciudad;

    @NotBlank(message = "El pais es obligatorio")
    @Column(name = "pais")
    private String pais;
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetallePedido> detalles;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    @NotNull(message = "El usuario es obligatorio")
    private Usuario usuario;

    public enum Estado {
        PENDIENTE, EN_PROCESO, COMPLETADO
    }

}