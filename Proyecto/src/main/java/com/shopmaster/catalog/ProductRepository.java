package com.shopmaster.catalog;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

/** Guarda productos de demostración en memoria mientras la aplicación está encendida. */
@Repository
public class ProductRepository {

    private final Map<Long, Product> productos = new ConcurrentHashMap<>();
    private final AtomicLong siguienteId = new AtomicLong(9);

    public ProductRepository() {
        // Catálogo inicial alineado con los productos de muestra del front-end.
        agregarInicial(1, "Tenis urbanos rojos", "Moda", 189900, 249900L, 12,
                "https://images.unsplash.com/photo-1542291026-7eec264c27ff?auto=format&fit=crop&w=900&q=85", "Más vendido");
        agregarInicial(2, "Gafas de sol clásicas", "Accesorios", 59900, 89900L, 18,
                "https://images.unsplash.com/photo-1572635196237-14b3f281503f?auto=format&fit=crop&w=900&q=85", "Oferta");
        agregarInicial(3, "Cámara instantánea", "Tecnología", 329900, 399900L, 7,
                "https://images.unsplash.com/photo-1526170375885-4d8ecf77b99f?auto=format&fit=crop&w=900&q=85", "Nuevo");
        agregarInicial(4, "Morral de viaje", "Accesorios", 119900, 159900L, 10,
                "https://images.unsplash.com/photo-1553062407-98eeb64c6a62?auto=format&fit=crop&w=900&q=85", "");
        agregarInicial(5, "Perfume edición especial", "Accesorios", 149900, 199900L, 14,
                "https://images.unsplash.com/photo-1541643600914-78b084683601?auto=format&fit=crop&w=900&q=85", "Oferta");
        agregarInicial(6, "Buzo cómodo con capucha", "Moda", 99900, 139900L, 9,
                "https://images.unsplash.com/photo-1556821840-3a63f95609a7?auto=format&fit=crop&w=900&q=85", "");
        agregarInicial(7, "Audífonos inalámbricos", "Tecnología", 219900, null, 11,
                "https://images.unsplash.com/photo-1505740420928-5e560c06d30e?auto=format&fit=crop&w=900&q=85", "Favorito");
        agregarInicial(8, "Reloj minimalista", "Accesorios", 179900, null, 6,
                "https://images.unsplash.com/photo-1523275335684-37898b6baf30?auto=format&fit=crop&w=900&q=85", "Nuevo");
    }

    private void agregarInicial(long id, String name, String category, long price, Long oldPrice,
                                int stock, String image, String tag) {
        productos.put(id, new Product(id, name, category, price, oldPrice, stock, image, tag));
    }

    public Collection<Product> findAll() { return productos.values(); }
    public Product findById(long id) { return productos.get(id); }
    public Product save(Product product) { productos.put(product.id(), product); return product; }
    public Product create(ProductRequest request) {
        long id = siguienteId.getAndIncrement();
        Product product = fromRequest(id, request);
        productos.put(id, product);
        return product;
    }
    public boolean delete(long id) { return productos.remove(id) != null; }
    public long nextId() { return siguienteId.getAndIncrement(); }

    public synchronized boolean decreaseStock(long id, int quantity) {
        Product current = productos.get(id);
        if (current == null || current.stock() < quantity) return false;
        productos.put(id, new Product(current.id(), current.name(), current.category(), current.price(),
                current.oldPrice(), current.stock() - quantity, current.image(), current.tag()));
        return true;
    }

    private Product fromRequest(long id, ProductRequest request) {
        return new Product(id, request.name().strip(), request.category().strip(), request.price(),
                request.oldPrice(), request.stock(), request.image().strip(),
                request.tag() == null ? "" : request.tag().strip());
    }
}
