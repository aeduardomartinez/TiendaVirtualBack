package com.loquierestecno.loquierestecnoBack.dto;

import com.loquierestecno.loquierestecnoBack.model.Usuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegistroUsuarioDTO {
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    private String apellido;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "Debe ser un correo válido")
    private String correo;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 5, message = "La contraseña debe tener al menos 5 caracteres")
    private String contrasena;

    @NotBlank(message = "La dirección es obligatoria")
    private String direccion;

    @NotBlank(message = "El telefono es obligatorio")
    private String telefono;

    private Usuario.Rol rol;
}
