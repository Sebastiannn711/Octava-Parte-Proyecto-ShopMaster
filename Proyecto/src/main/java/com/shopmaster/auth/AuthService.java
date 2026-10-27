package com.shopmaster.auth;

import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/** Contiene las reglas de registro y autenticación de esta demostración. */
@Service
public class AuthService {

    // La evidencia usa memoria para mantener el ejemplo sencillo; se pierde al apagar la aplicación.
    private final Map<String, String> usuariosRegistrados = new ConcurrentHashMap<>();
    private final PasswordEncoder codificador = new BCryptPasswordEncoder();

    /** Registra el usuario y guarda una huella de la contraseña, nunca el texto original. */
    public boolean registrar(String usuario, String contrasena) {
        String usuarioNormalizado = normalizarUsuario(usuario);
        String contrasenaCodificada = codificador.encode(contrasena);

        // putIfAbsent evita reemplazar la cuenta si ya existe ese nombre de usuario.
        return usuariosRegistrados.putIfAbsent(usuarioNormalizado, contrasenaCodificada) == null;
    }

    /** Compara la contraseña recibida con la huella guardada durante el registro. */
    public boolean autenticar(String usuario, String contrasena) {
        String contrasenaCodificada = usuariosRegistrados.get(normalizarUsuario(usuario));
        return contrasenaCodificada != null && codificador.matches(contrasena, contrasenaCodificada);
    }

    private String normalizarUsuario(String usuario) {
        return usuario.strip().toLowerCase(Locale.ROOT);
    }
}