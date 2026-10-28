package repository;
import entities.Product;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ProductRepositoryImpl implements ProductRepository {
    private final List<Product> productsList = new ArrayList<>();
    private final FileProductDataSource dataSource;

    public ProductRepositoryImpl(String fileName) {
        this.dataSource = new FileProductDataSource(fileName);
    }

    @Override
    public void loadProductsFromFile() throws Exception {
        List<Product> loadedProducts = dataSource.readProducts();
        productsList.clear();
        productsList.addAll(loadedProducts);
    }

    @Override
    public void saveProductsToFile() throws Exception {
        dataSource.writeProducts(productsList);
    }

    @Override
    public List<Product> getAllProducts() {
        return productsList;
    }

    @Override
    public Optional<Product> findById(String id) {
        return productsList.stream()
                .filter(product -> product.getProductID().equals(id))
                .findFirst();
    }

    @Override
    public List<Product> findProducts(Predicate<Product> condition) {
        return productsList.stream()
                .filter(condition)
                .collect(Collectors.toList());
    }

    @Override
    public boolean addProduct(Product product) {
        if (product == null || exists(product.getProductID())) {
            return false;  
        }
        return productsList.add(product);
    }

    @Override
    public boolean updateProduct(Product product) {
        Optional<Product> existingProductOpt = findById(product.getProductID());
        if (!existingProductOpt.isPresent()) {
            return false;
        }
        int index = productsList.indexOf(existingProductOpt.get());
        productsList.set(index, product);
        return true;
    }

    @Override 
    public boolean deleteProduct(String id) {
        return productsList.removeIf(product ->
                product.getProductID().equalsIgnoreCase(id));
    }
    
    private boolean exists(String productId) {
        return productsList.stream()
                .anyMatch(product -> product.getProductID().equalsIgnoreCase(productId));
    }
}