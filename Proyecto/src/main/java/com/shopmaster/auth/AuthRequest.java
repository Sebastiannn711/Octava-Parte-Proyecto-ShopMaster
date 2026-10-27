package com.shopmaster.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/** Datos que el cliente envía en JSON para registrarse o iniciar sesión. */
public record AuthRequest(
        @NotBlank(message = "El usuario es obligatorio.")
        @Size(min = 3, max = 30, message = "El usuario debe tener entre 3 y 30 caracteres.")
        String usuario,

        @NotBlank(message = "La contraseña es obligatoria.")
        @Size(min = 8, max = 64, message = "La contraseña debe tener entre 8 y 64 caracteres.")
        String contrasena
) {
}