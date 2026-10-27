package com.shopmaster.orders;

import java.time.Instant;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import com.shopmaster.catalog.Product;
import com.shopmaster.catalog.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/** Valida el inventario y registra pedidos de demostración en memoria. */
@Service
public class OrderService {

    private final ProductService products;
    private final Map<String, StoredOrder> orders = new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong(100001);

    public OrderService(ProductService products) { this.products = products; }

    public OrderResponse create(OrderRequest request) {
        // Si un mismo producto aparece dos veces, consolidamos sus cantidades antes de validar el inventario.
        Map<Long, Integer> quantities = new LinkedHashMap<>();
        for (OrderItemRequest item : request.items()) {
            if (item.quantity() <= 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cada cantidad debe ser al menos 1.");
            }
            quantities.merge(item.productId(), item.quantity(), Math::addExact);
        }

        long total = 0;
        int itemCount = 0;
        for (Map.Entry<Long, Integer> entry : quantities.entrySet()) {
            Product product = products.findProduct(entry.getKey());
            int quantity = entry.getValue();
            if (product.stock() < quantity) {
                throw new ResponseStatusException(HttpStatus.CONFLICT,
                        "No hay inventario suficiente para " + product.name() + ".");
            }
            total = Math.addExact(total, Math.multiplyExact(product.price(), quantity));
            itemCount += quantity;
        }

        // El servidor descuenta inventario solo después de validar todos los artículos del pedido.
        for (Map.Entry<Long, Integer> entry : quantities.entrySet()) {
            products.reserveStock(entry.getKey(), entry.getValue());
        }

        String id = "SM-" + sequence.getAndIncrement();
        OrderResponse summary = new OrderResponse(id, "RECIBIDO_DEMO", total,
                request.paymentMethod().strip(), Instant.now(), itemCount);
        orders.put(id, new StoredOrder(summary, request));
        return summary;
    }

    public OrderResponse findById(String id) {
        StoredOrder order = orders.get(id);
        if (order == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontró el pedido.");
        return order.summary();
    }

    public List<OrderResponse> findAll() {
        return orders.values().stream().map(StoredOrder::summary)
                .sorted(Comparator.comparing(OrderResponse::createdAt).reversed()).toList();
    }

    // Conserva los datos de entrega durante la sesión sin incluirlos en la respuesta pública.
    private record StoredOrder(OrderResponse summary, OrderRequest deliveryDetails) { }
}
