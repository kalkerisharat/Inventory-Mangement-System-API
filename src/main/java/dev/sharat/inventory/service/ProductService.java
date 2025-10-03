package dev.sharat.inventory.service;
import dev.sharat.inventory.exception.InvalidStockOperationException;
import dev.sharat.inventory.model.Product;
import dev.sharat.inventory.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    // CRUD Operations
    // Updated to return Page<Product>
    public Page<Product> getAllProducts(Pageable pageable) {
        return repository.findAll(pageable);
    }
    public Product getProductById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new InvalidStockOperationException("Product not found with id: " + id));
    }

    public Product createProduct(Product product) {
        validateStockQuantity(product.getStockQuantity());
        return repository.save(product);
    }

    public Product updateProduct(Long id, Product updatedProduct) {
        Product existing = getProductById(id);
        existing.setName(updatedProduct.getName());
        existing.setDescription(updatedProduct.getDescription());
        existing.setLowStockThreshold(updatedProduct.getLowStockThreshold());
        // Stock quantity update handled separately; here we just validate if provided
        if (updatedProduct.getStockQuantity() >= 0) {
            existing.setStockQuantity(updatedProduct.getStockQuantity());
        }
        return repository.save(existing);
    }

    public void deleteProduct(Long id) {
        repository.deleteById(id);
    }

    // Inventory Management
    public Product addStock(Long id, int quantity) {
        if (quantity <= 0) {
            throw new InvalidStockOperationException("Quantity to add must be positive");
        }
        Product product = getProductById(id);
        product.setStockQuantity(product.getStockQuantity() + quantity);
        return repository.save(product);
    }

    public Product removeStock(Long id, int quantity) {
        if (quantity <= 0) {
            throw new InvalidStockOperationException("Quantity to remove must be positive");
        }
        Product product = getProductById(id);
        if (product.getStockQuantity() < quantity) {
            throw new InvalidStockOperationException("Insufficient stock: available " + product.getStockQuantity() + ", requested " + quantity);
        }
        product.setStockQuantity(product.getStockQuantity() - quantity);
        return repository.save(product);
    }

    // Bonus: List low-stock products
    public List<Product> getLowStockProducts() {
        return repository.findLowStockProducts();
    }

    private void validateStockQuantity(int quantity) {
        if (quantity < 0) {
            throw new InvalidStockOperationException("Stock quantity cannot be negative");
        }
    }
}