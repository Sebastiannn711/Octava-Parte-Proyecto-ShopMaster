package com.shopmaster.orders;

import java.time.Instant;

/** Resumen público del pedido, sin exponer dirección ni teléfono del cliente. */
public record OrderResponse(
        String id,
        String status,
        long total,
        String paymentMethod,
        Instant createdAt,
        int itemCount
) {
}
