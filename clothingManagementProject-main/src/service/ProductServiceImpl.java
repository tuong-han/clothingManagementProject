package service;

import entities.Product;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import repository.ProductRepository;
import java.util.stream.Collectors;
import java.util.Collections;


public class ProductServiceImpl implements ProductService  {
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override 
    public Optional<Product> findById(String id) {
        return productRepository.findById(id) ;
    }

    @Override 
    public boolean addProduct(Product product) {
        return productRepository.addProduct(product);
    }

    @Override 
    public boolean updateProduct(Product product) {
        return productRepository.updateProduct(product);
    }

    @Override
    public List<Product> searchProducts(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return Collections.emptyList();
        }
        String normalizedKeyword = keyword.trim().toLowerCase();
        return productRepository.findProducts(product ->
                product.getProductName().toLowerCase().contains(normalizedKeyword));
    }

    @Override
    public List<Product> getAllProductsFromFile() throws Exception {
        productRepository.loadProductsFromFile();
        return productRepository.getAllProducts();
    }

    @Override
    public void saveProductsToFile() throws Exception {
        productRepository.saveProductsToFile();
    }

    @Override
    public boolean removeProduct(String id) {
        return productRepository.deleteProduct(id);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.getAllProducts();
    }

    @Override 
    public List<Product> sortBy(List<Product> productList, Comparator<Product> comparator) {
        return productList.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }
}
