package entities;
import util.Datavalidation;

public class Product {
    
    private String productID;
    private String productName;
    private String category;
    private String size;
    private String color ;
    private double price ;
    private int quantity;
    
    public Product(){
    }
    
    public Product(String ID, String name, 
                String category, String size, 
                String color, double price, int quantity){
        if (Datavalidation.isEmpty(ID) || Datavalidation.isEmpty(name)
                || Datavalidation.isEmpty(category) || Datavalidation.isEmpty(size)
                || Datavalidation.isEmpty(color)) {
            throw new IllegalArgumentException("Product fields cannot be empty.");
        }
        if (!Datavalidation.isValidPrice(price)) {
            throw new IllegalArgumentException("Price must be greater than 0.");
        }
        if (!Datavalidation.isValidQuantity(quantity)) {
            throw new IllegalArgumentException("Quantity must be non-negative.");
        }
        this.productID = ID;
        this.productName = name;
        this.category = category;
        this.size = size;
        this.color = color;
        this.price = price;
        this.quantity = quantity;
    }
    
    // Getter

    public String getProductID() {
        return productID;
    }

    public String getProductName() {
        return productName;
    }

    public String getCategory() {
        return category;
    }

    public String getSize() {
        return size;
    }

    public String getColor() {
        return color;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }
    
    //Setter
        // Không có setter cho productID vì productID là khóa chính và không được thay đổi sau khi tạo đối tượng

    public void setProductName(String productName) {
        // Kiểm tra chuỗi rỗng
        if (Datavalidation.isEmpty(productName)) {
            throw new IllegalArgumentException("Product name cannot be empty");
        }
        this.productName = productName;
    }

    public void setCategory(String category) {
        // Kiểm tra chuỗi rỗng
        if (Datavalidation.isEmpty(category)) {
            throw new IllegalArgumentException("Category cannot be empty");
        }
        this.category = category;
    }

    public void setSize(String size) {
        // Kiểm tra chuỗi rỗng
        if (Datavalidation.isEmpty(size)) {
            throw new IllegalArgumentException("Size cannot be empty");
        }
        this.size = size;
    }

    public void setColor(String color) {
        // Kiểm tra chuỗi rỗng
        if (Datavalidation.isEmpty(color)) {
            throw new IllegalArgumentException("Color cannot be empty");
        }
        this.color = color;
    }

    public void setPrice(float price) {
        // Kiểm tra giá sản phẩm > 0
        if (!Datavalidation.isValidPrice(price)) {
            throw new IllegalArgumentException("Price must be greater than 0");
        }
        this.price = price;
    }

    public void setQuantity(int quantity) {
        // Kiểm tra số lượng tồn kho >= 0
        if (!Datavalidation.isValidQuantity(quantity)) {
            throw new IllegalArgumentException("Quantity must be a non-negative integer");
        }
        this.quantity = quantity;
    }
    
    public void reduceStock(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount to reduce stock cannot be negative");
        }
        // Kiểm tra số lượng tồn kho >= 0
        if (!Datavalidation.isValidQuantity(quantity - amount)) {
            throw new IllegalArgumentException("Insufficient stock");
        }
        this.quantity = quantity - amount;
    }
    
    public boolean isLowStock() {
        return quantity < 6; 
    }

    public String toCSV() {
        return String.format("%s,%s,%s,%s,%s,%.2f,%d", 
                productID, productName, category, size, color,
                price, quantity);
    }

    @Override
    public String toString(){
        return String.format("%s, %s, %s, %s, %s, %.2f, %d", 
                productID, productName, category, size, color,
                price, quantity);
    }
            
}
