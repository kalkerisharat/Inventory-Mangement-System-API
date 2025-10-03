package dev.sharat.inventory.controller;

import dev.sharat.inventory.model.Product;
import dev.sharat.inventory.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@Tag(name = "Product API", description = "Endpoints for managing products and inventory")
public class ProductController {

    @Autowired
    private ProductService service;

    @Operation(summary = "Get all products", description = "Retrieves a paginated list of all products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful retrieval")
    })
    @GetMapping
    public Page<Product> getAll(@Parameter(description = "Pagination details (e.g., ?page=0&size=10&sort=stockQuantity,desc)") Pageable pageable) {
        return service.getAllProducts(pageable);
    }

    @Operation(summary = "Get a product by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product found"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @GetMapping("/{id}")
    public Product getById(@PathVariable Long id) {
        return service.getProductById(id);
    }

    @Operation(summary = "Create a new product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product created"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping
    public ResponseEntity<Product> create(@Valid @RequestBody Product product) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createProduct(product));
    }

    @Operation(summary = "Update an existing product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product updated"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @PutMapping("/{id}")
    public Product update(@PathVariable Long id, @Valid @RequestBody Product product) {
        return service.updateProduct(id, product);
    }

    @Operation(summary = "Delete a product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Product deleted"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Add stock to a product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Stock added"),
            @ApiResponse(responseCode = "400", description = "Invalid quantity"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @PostMapping("/{id}/add-stock")
    public Product addStock(@PathVariable Long id, @RequestParam @Positive int quantity) {
        return service.addStock(id, quantity);
    }

    @Operation(summary = "Remove stock from a product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Stock removed"),
            @ApiResponse(responseCode = "400", description = "Invalid quantity or insufficient stock"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @PostMapping("/{id}/remove-stock")
    public Product removeStock(@PathVariable Long id, @RequestParam @Positive int quantity) {
        return service.removeStock(id, quantity);
    }

    @Operation(summary = "Get low-stock products", description = "Lists products below their low stock threshold")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful retrieval")
    })
    @GetMapping("/low-stock")
    public List<Product> getLowStock() {
        return service.getLowStockProducts();
    }
}