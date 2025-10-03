package dev.sharat.inventory.service;
import dev.sharat.inventory.exception.InvalidStockOperationException;
import dev.sharat.inventory.model.Product;
import dev.sharat.inventory.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository repository;

    @InjectMocks
    private ProductService service;

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setId(1L);
        product.setStockQuantity(20);
    }

    @Test
    void addStock_success() {
        when(repository.findById(1L)).thenReturn(Optional.of(product));
        when(repository.save(any(Product.class))).thenReturn(product);

        Product updated = service.addStock(1L, 10);
        assertEquals(30, updated.getStockQuantity());
    }

    @Test
    void addStock_invalidQuantity() {
        assertThrows(InvalidStockOperationException.class, () -> service.addStock(1L, -5));
    }

    @Test
    void removeStock_success() {
        when(repository.findById(1L)).thenReturn(Optional.of(product));
        when(repository.save(any(Product.class))).thenReturn(product);

        Product updated = service.removeStock(1L, 10);
        assertEquals(10, updated.getStockQuantity());
    }

    @Test
    void removeStock_insufficientStock() {
        when(repository.findById(1L)).thenReturn(Optional.of(product));
        assertThrows(InvalidStockOperationException.class, () -> service.removeStock(1L, 25));
    }

    @Test
    void removeStock_invalidQuantity() {
        assertThrows(InvalidStockOperationException.class, () -> service.removeStock(1L, 0));
    }

    @Test
    void createProduct_negativeStock() {
        Product invalid = new Product();
        invalid.setStockQuantity(-1);
        assertThrows(InvalidStockOperationException.class, () -> service.createProduct(invalid));
    }
}