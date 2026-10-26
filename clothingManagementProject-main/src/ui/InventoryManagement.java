package ui;

import entities.Product;
import service.ProductService;
import util.DataInput;

import java.util.Optional;

public class InventoryManagement {
    private final ProductService productService;
    private final ProductManagement products;

    public InventoryManagement(ProductService productService, ProductManagement products) {
        this.productService = productService;
        this.products = products;
    }

    public void printLowStockProducts() {
        boolean found = false;
        for (Product product : productService.getAllProducts()) {
            if (product.isLowStock()) {
                System.out.println(product);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No low-stock products.");
        }
    }

    public void adjustProductStock() throws Exception {
        String productId = DataInput.getString("Product ID: ").trim();
        Optional<Product> product = productService.findById(productId);
        if (!product.isPresent()) {
            System.out.println("Product not found.");
            return;
        }

        int quantity = DataInput.getIntegerNumber("New stock quantity: ");
        product.get().setQuantity(quantity);
        if (productService.updateProduct(product.get())) {
            productService.saveProductsToFile();
            System.out.println("Stock updated and saved.");
        } else {
            System.out.println("Could not update stock.");
        }
    }
