package dev.sharat.inventory.repository;
import dev.sharat.inventory.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    //Query for low-stock products
    @Query("SELECT p FROM Product p WHERE p.stockQuantity < p.lowStockThreshold")
    List<Product> findLowStockProducts();
}