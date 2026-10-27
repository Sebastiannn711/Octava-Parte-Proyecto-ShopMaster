package com.shopmaster.catalog;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/** Servicios públicos para mostrar el catálogo y el detalle de un producto. */
@RestController
@RequestMapping("/api")
public class ProductController {

    private final ProductService service;
    public ProductController(ProductService service) { this.service = service; }

    @GetMapping("/productos")
    public List<Product> list(@RequestParam(required = false) String categoria,
                              @RequestParam(name = "q", required = false) String query) {
        return service.findProducts(categoria, query);
    }

    @GetMapping("/productos/{id}")
    public Product detail(@PathVariable long id) { return service.findProduct(id); }

    @GetMapping("/categorias")
    public List<String> categories() { return service.findCategories(); }
}


