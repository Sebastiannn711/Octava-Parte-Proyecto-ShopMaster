package com.shopmaster.orders;

import java.net.URI;
import java.util.List;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Servicios para crear pedidos y consultar su resumen. */
@RestController
@RequestMapping("/api")
public class OrderController {

    private final OrderService service;
    public OrderController(OrderService service) { this.service = service; }

    @PostMapping("/pedidos")
    public ResponseEntity<OrderResponse> create(@Valid @RequestBody OrderRequest request) {
        OrderResponse created = service.create(request);
        return ResponseEntity.created(URI.create("/api/pedidos/" + created.id())).body(created);
    }

    @GetMapping("/pedidos/{id}")
    public OrderResponse findById(@PathVariable String id) { return service.findById(id); }

    @GetMapping("/admin/pedidos")
    public List<OrderResponse> listForAdmin() { return service.findAll(); }
}
