package repository;

import entities.Product;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import util.FileManager;



public class FileProductDataSource {
    private final FileManager fileManager;

    public FileProductDataSource(String fileName){
        fileManager = new FileManager(fileName);
    }

    public List<Product> readProducts() throws Exception {
        List<Product> productList = new ArrayList<>();

        List<String> data = fileManager.readDataFromFile();
        int lineNumber = 0;
        for (String line : data) {
            lineNumber++;
            if (line == null || line.trim().isEmpty()) {
                continue;
            }
            try {
                List<String> productData = Arrays.asList(line.split(","));
                String id = productData.get(0).trim();
                String name = productData.get(1).trim();
                String category = productData.get(2).trim();
                String size = productData.get(3).trim();
                String color = productData.get(4).trim();
                double price = Double.parseDouble(productData.get(5).trim());
                int quantity = Integer.parseInt(productData.get(6).trim());

                productList.add(new Product(id, name, category, size, color, price, quantity));
            } catch (RuntimeException ex) {
                throw new Exception("Invalid product data at line " + lineNumber + ".", ex);
            }
        }
        return productList;
    }

    public void writeProducts(List<Product> products) throws Exception {
        List<String> lines = products.stream()
                .map(Product::toCSV)
                .collect(Collectors.toList());
        fileManager.saveDataToFile(String.join(System.lineSeparator(), lines));
    }
}
