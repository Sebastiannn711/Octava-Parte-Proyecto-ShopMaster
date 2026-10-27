package com.shopmaster.catalog;

/** Datos de catálogo que ve el comprador y administra la tienda. */
public record Product(
        long id,
        String name,
        String category,
        long price,
        Long oldPrice,
        int stock,
        String image,
        String tag
) {
}
