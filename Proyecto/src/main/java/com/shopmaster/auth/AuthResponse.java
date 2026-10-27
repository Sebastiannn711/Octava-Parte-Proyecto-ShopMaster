package com.shopmaster.auth;

/** Respuesta breve que la API devuelve en formato JSON. */
public record AuthResponse(boolean exitoso, String mensaje) {
}