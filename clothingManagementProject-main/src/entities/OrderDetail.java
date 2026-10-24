package entities;

public class OrderDetail {
    private Product product;
    private int quantity;
    private double price;

    public OrderDetail(Product product, int quantity) {
        if (product == null) {
            throw new IllegalArgumentException("Product must exist.");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException(
                    "Quantity must be greater than zero."
            );
        }
        if (quantity > product.getQuantity()) {
            throw new IllegalArgumentException("Not enough stock.");
        }

        this.product = product;
        this.quantity = quantity;
        this.price = product.getPrice();
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public double calculateAmount() {
        return price * quantity;
    }
}
