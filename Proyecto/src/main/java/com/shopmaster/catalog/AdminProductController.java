package com.shopmaster.catalog;

import java.net.URI;
import java.util.List;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Operaciones de muestra para que el panel administrativo gestione productos. */
@RestController
@RequestMapping("/api/admin/productos")
public class AdminProductController {

    private final ProductService service;
    public AdminProductController(ProductService service) { this.service = service; }

    @GetMapping
    public List<Product> list() { return service.findProducts(null, null); }

    @PostMapping
    public ResponseEntity<Product> create(@Valid @RequestBody ProductRequest request) {
        Product created = service.create(request);
        return ResponseEntity.created(URI.create("/api/productos/" + created.id())).body(created);
    }

    @PutMapping("/{id}")
    public Product update(@PathVariable long id, @Valid @RequestBody ProductRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
