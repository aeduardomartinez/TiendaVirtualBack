package com.loquierestecno.loquierestecnoBack.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Data
@Table(name = "usuario")
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede tener más de 100 caracteres")
    @Column(name = "nombre")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(max = 100, message = "El nombre no puede tener más de 100 caracteres")
    @Column(name = "apellido")
    private String apellido;

    @NotBlank(message = "El telefono es obligatorio")
    @Size(max = 12, message = "El telefono no puede tener más de 12 caracteres")
    @Column(name = "telefono")
    private String telefono;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "Debe ser un correo válido")
    @Size(max = 100, message = "El correo no puede tener más de 100 caracteres")
    @Column(name = "correo")
    private String correo;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 5, message = "La contraseña debe tener al menos 20 caracteres")
    @Column(name = "password")
    private String contrasena;

    @NotBlank(message = "La dirección es obligatoria")
    @Size(max = 255, message = "La dirección no puede tener más de 255 caracteres")
    @Column(name = "direccion")
    private String direccion;

    @NotNull(message = "El rol es obligatorio")
    @Enumerated(EnumType.STRING)
    private Rol rol;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return "";
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }

    public enum Rol {
        ROLE_CLIENTE, ROLE_ADMINISTRADOR
    }

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pedido> pedidos;


}