package service;
import entities.Product;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public interface ProductService {
            
    boolean addProduct(Product product);

    Optional<Product> findById(String id);
    
    boolean updateProduct(Product product);

    boolean removeProduct(String id);

    List<Product> searchProducts(String keyword);

    List<Product> getAllProducts();

    List<Product> getAllProductsFromFile() throws Exception;
    
    List<Product> sortBy(List<Product> productList, Comparator<Product> comparator);
    
    void saveProductsToFile() throws Exception;
}
