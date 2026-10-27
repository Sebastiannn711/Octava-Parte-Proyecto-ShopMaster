package com.shopmaster.orders;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;

/** Identificador y cantidad de cada producto que se desea comprar. */
public record OrderItemRequest(
        @Positive(message = "El identificador del producto debe ser positivo.")
        long productId,
        @Positive(message = "La cantidad debe ser al menos 1.")
        @Max(value = 100, message = "La cantidad máxima por producto es 100.")
        int quantity
) {
}
