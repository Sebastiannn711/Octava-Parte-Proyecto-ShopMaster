package com.shopmaster.catalog;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

/** Datos que el panel administrativo envía para crear o actualizar un producto. */
public record ProductRequest(
        @NotBlank(message = "El nombre del producto es obligatorio.")
        String name,
        @NotBlank(message = "La categoría es obligatoria.")
        String category,
        @Positive(message = "El precio debe ser mayor que cero.")
        long price,
        @Positive(message = "El precio anterior debe ser mayor que cero.")
        Long oldPrice,
        @PositiveOrZero(message = "El inventario no puede ser negativo.")
        int stock,
        @NotBlank(message = "La dirección de la imagen es obligatoria.")
        String image,
        String tag
) {
}
