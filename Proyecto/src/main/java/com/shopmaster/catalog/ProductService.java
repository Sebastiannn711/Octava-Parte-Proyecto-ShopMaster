package com.shopmaster.catalog;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/** Aplica las reglas para consultar y administrar el catálogo. */
@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) { this.repository = repository; }

    public List<Product> findProducts(String category, String query) {
        String normalizedCategory = category == null ? "" : category.strip();
        String normalizedQuery = query == null ? "" : query.strip().toLowerCase(Locale.ROOT);
        return repository.findAll().stream()
                .filter(product -> normalizedCategory.isBlank() || normalizedCategory.equalsIgnoreCase("Todo")
                        || product.category().equalsIgnoreCase(normalizedCategory))
                .filter(product -> normalizedQuery.isBlank()
                        || product.name().toLowerCase(Locale.ROOT).contains(normalizedQuery)
                        || product.category().toLowerCase(Locale.ROOT).contains(normalizedQuery))
                .sorted(Comparator.comparingLong(Product::id))
                .toList();
    }

    public Product findProduct(long id) {
        Product product = repository.findById(id);
        if (product == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontró el producto.");
        return product;
    }

    public List<String> findCategories() {
        return repository.findAll().stream().map(Product::category).distinct().sorted().toList();
    }

    public Product create(ProductRequest request) { return repository.create(request); }

    public Product update(long id, ProductRequest request) {
        findProduct(id);
        return repository.save(new Product(id, request.name().strip(), request.category().strip(), request.price(),
                request.oldPrice(), request.stock(), request.image().strip(),
                request.tag() == null ? "" : request.tag().strip()));
    }

    public void delete(long id) {
        if (!repository.delete(id)) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontró el producto.");
    }

    public Product reserveStock(long id, int quantity) {
        Product product = findProduct(id);
        if (!repository.decreaseStock(id, quantity)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "No hay inventario suficiente para " + product.name() + ".");
        }
        return findProduct(id);
    }
}


