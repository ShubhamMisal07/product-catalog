package com.example.productcatalog.controller;

import com.example.productcatalog.model.Product;
import com.example.productcatalog.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@SecurityRequirement(name = "bearerAuth")
public class ProductController {

    private final ProductService service;
    private static final String ADMIN_ROLE= "hasRole('ADMIN')";
    private static final String ADMIN_OR_SALES_ROLE = "hasAnyRole('ADMIN', 'SALES')";

    public ProductController(ProductService service) {
        this.service = service;
    }

    @Operation(summary = "Get all products", description = "Retrieve paginated list of products")
    @GetMapping
    @PreAuthorize(ADMIN_OR_SALES_ROLE)
    public ResponseEntity<Page<Product>> getAllProducts(Pageable pageable) {
        return ResponseEntity.ok(service.getAllProducts(pageable));
    }

    @Operation(summary = "Create a new product", description = "Requires ADMIN role")
    @PostMapping
@ResponseStatus(HttpStatus.CREATED)
@PreAuthorize("hasRole('ADMIN')")
public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product) {
    Product createdProduct = service.createProduct(product);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
}

    @Operation(summary = "Get product by ID", description = "Retrieve a specific product")
    @GetMapping("/{id}")
    @PreAuthorize(ADMIN_OR_SALES_ROLE)
    public ResponseEntity<Product> getProductById(@PathVariable String id) {
        return ResponseEntity.ok(service.getProductById(id));
    }

    @Operation(summary = "Update product", description = "Update existing product details")
    @PutMapping("/{id}")
    @PreAuthorize(ADMIN_ROLE)
    public ResponseEntity<Product> updateProduct(
            @PathVariable String id, 
            @Valid @RequestBody Product product) {
        return ResponseEntity.ok(service.updateProduct(id, product));
    }

    @Operation(summary = "Delete product", description = "Remove a product from catalog")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize(ADMIN_ROLE)
    public ResponseEntity<Void> deleteProduct(@PathVariable String id) {
        service.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}