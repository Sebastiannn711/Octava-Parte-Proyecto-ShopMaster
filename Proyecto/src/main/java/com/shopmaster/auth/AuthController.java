package com.shopmaster.auth;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Recibe las solicitudes HTTP para registrar e iniciar sesión. */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService servicioAutenticacion;

    public AuthController(AuthService servicioAutenticacion) {
        this.servicioAutenticacion = servicioAutenticacion;
    }

    /** Crea una cuenta de demostración y responde con estado 201 si se registró. */
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> registrar(@Valid @RequestBody AuthRequest solicitud) {
        boolean registrado = servicioAutenticacion.registrar(solicitud.usuario(), solicitud.contrasena());

        if (!registrado) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new AuthResponse(false, "Ese usuario ya está registrado."));
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new AuthResponse(true, "Registro satisfactorio."));
    }

    /** Verifica las credenciales y devuelve 401 cuando no coinciden. */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> iniciarSesion(@Valid @RequestBody AuthRequest solicitud) {
        boolean autenticado = servicioAutenticacion.autenticar(solicitud.usuario(), solicitud.contrasena());

        if (!autenticado) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new AuthResponse(false, "Error en la autenticación: usuario o contraseña incorrectos."));
        }

        return ResponseEntity.ok(new AuthResponse(true, "Autenticación satisfactoria."));
    }
}