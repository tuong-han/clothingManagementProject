/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;
import java.util.List;
import java.util.Optional;
import entities.Product; 
import java.util.function.Predicate;

/**
 *
 * @author admin
 */
public interface ProductRepository {
    List<Product> getAllProducts();
    Optional<Product> findById(String id);    
    List<Product> findProducts(Predicate<Product> condition);
    boolean addProduct(Product product);
    boolean updateProduct(Product product);
    boolean deleteProduct(String id);
    void loadProductsFromFile() throws Exception;
    void saveProductsToFile() throws Exception;
}
