package ui;

import entities.Product;
import service.ProductService;

import util.DataInput;
// import util.Datavalidation;

import java.util.Collections;
// import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class ProductManagement {
    private final ProductService productService;

    public ProductManagement(ProductService productService) {
        this.productService = productService;
    }

    public void print(List<Product> products) throws Exception {
        System.out.println(String.join("", Collections.nCopies(50, "-")));
        System.out.printf("%-10s%-20s%-10s%-10s%n", "ID", "Name", "Price", "Quantity");
        System.out.println(String.join("", Collections.nCopies(50, "-")));
        for (Product product : products) {
            System.out.printf("%-10s%-20s%-10.2f%-10d%n",
                    product.getProductID(),
                    product.getProductName(),
                    product.getPrice(),
                    product.getQuantity());
        }
    }
    //---------------------------------------------------
    public void printProductListFromFile() throws Exception {
        List<Product> products = productService.getAllProductsFromFile();
        print(products);
    }

    public void printAllProducts() {
        List<Product> products = productService.getAllProducts();
        if (products.isEmpty()) {
            System.out.println("No products available.");
        } else {
            System.out.println("Product List:");
            for (Product product : products) {
                System.out.println(product);
            }
        }
    }

    public void searchProducts() throws Exception {
        String keyword = DataInput.getString("Enter product name: ").trim();
        List<Product> matches = productService.searchProducts(keyword);
        if (matches.isEmpty()) {
            System.out.println("No matching products.");
            return;
        }
        print(matches);
    }

    public void addProduct() {
        String id, name, category, size, color;
        double price;
        int quantity;
        try {
            id = DataInput.getString("Enter product ID: ");
            name = DataInput.getString("Enter product name: ");
            price = DataInput.getDoubleNumber("Enter product price: ");
            quantity = DataInput.getIntegerNumber("Enter product quantity: ");
            category = DataInput.getString("Enter product category: ");
            size = DataInput.getString("Enter product size: ");
            color = DataInput.getString("Enter product color: ");

            Product product = new Product(id, name, category, size, color, (float) price, quantity);
            boolean isAdded = productService.addProduct(product);
            if (isAdded) {
                System.out.println("Product added successfully.");
            } else {
                System.out.println("Failed to add product. Product with the same ID may already exist.");
            }
        } catch (Exception e) {
            System.out.println("Error adding product: " + e.getMessage());
        }
    }
    public void removeProduct(){
        String id;
        try {
            id = DataInput.getString("Enter product ID to remove: ");
            boolean isRemoved = productService.removeProduct(id);
            if (isRemoved) {
                System.out.println("Product removed successfully.");
            } else {
                System.out.println("Failed to remove product. Product with the given ID may not exist.");
            }
        } catch (Exception e) {
            System.out.println("Error removing product: " + e.getMessage());
        }
    }

    public void updateProduct() {
        String id, name, category, size, color;
        double price;
        int quantity;
        try {
            id = DataInput.getString("Enter product ID to update: ");
            Optional<Product> existingProductOpt = productService.findById(id);
            if (existingProductOpt.isPresent()) {
                name = DataInput.getString("Enter new product name: ");
                category = DataInput.getString("Enter new product category: ");
                size = DataInput.getString("Enter new product size: ");
                price = DataInput.getDoubleNumber("Enter new product price: ");
                quantity = DataInput.getIntegerNumber("Enter new product quantity: ");
                color = DataInput.getString("Enter new product color: ");

                Product updatedProduct = new Product(id, name, category, size, color, (float) price, quantity);
                boolean isUpdated = productService.updateProduct(updatedProduct);
                if (isUpdated) {
                    System.out.println("Product updated successfully.");
                } else {
                    System.out.println("Failed to update product.");
                }
            } else {
                System.out.println("Product with the given ID does not exist.");
            }
        } catch (Exception e) {
            System.out.println("Error updating product: " + e.getMessage());
        }
    }
}