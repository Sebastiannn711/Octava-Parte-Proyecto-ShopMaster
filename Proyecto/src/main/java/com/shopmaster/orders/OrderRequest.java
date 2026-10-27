package com.shopmaster.orders;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

/** Datos mínimos de entrega y productos enviados para crear un pedido de demostración. */
public record OrderRequest(
        @NotBlank(message = "El nombre del cliente es obligatorio.")
        String customerName,
        @NotBlank(message = "El celular es obligatorio.")
        String phone,
        @NotBlank(message = "El correo es obligatorio.") @Email(message = "El correo no tiene un formato válido.")
        String email,
        @NotBlank(message = "La dirección es obligatoria.")
        String address,
        @NotBlank(message = "La ciudad es obligatoria.")
        String city,
        @NotBlank(message = "El departamento es obligatorio.")
        String department,
        @NotBlank(message = "La forma de pago es obligatoria.")
        String paymentMethod,
        @NotEmpty(message = "El pedido debe tener al menos un producto.")
        @Size(max = 30, message = "El pedido no puede tener más de 30 líneas.")
        List<@Valid OrderItemRequest> items
) {
}
