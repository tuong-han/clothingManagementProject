package entities;

import java.util.ArrayList;

public class Order {
    private String transactionId;
    private String date;
    private Customer customer;
    private ArrayList<OrderDetail> orderDetails;
    private boolean completed;

    public Order(String transactionId, String date, Customer customer) {
        if (transactionId == null || transactionId.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Transaction ID cannot be empty."
            );
        }
        if (date == null || date.trim().isEmpty()) {
            throw new IllegalArgumentException("Date cannot be empty.");
        }
        if (customer == null) {
            throw new IllegalArgumentException("Customer must exist.");
        }

        this.transactionId = transactionId;
        this.date = date;
        this.customer = customer;
        this.orderDetails = new ArrayList<>();
        this.completed = false;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getDate() {
        return date;
    }

    public Customer getCustomer() {
        return customer;
    }

    public ArrayList<OrderDetail> getOrderDetails() {
        return new ArrayList<>(orderDetails);
    }

    public boolean isCompleted() {
        return completed;
    }

    public void addOrderDetail(OrderDetail detail) {
        if (completed) {
            throw new IllegalStateException(
                    "Cannot modify a completed order."
            );
        }
        if (detail == null) {
            throw new IllegalArgumentException(
                    "Order detail cannot be empty."
            );
        }

        for (OrderDetail existing : orderDetails) {
            if (existing.getProduct() == detail.getProduct()) {
                throw new IllegalArgumentException(
                        "This product is already in the order."
                );
            }
        }

        orderDetails.add(detail);
    }

    public double calculateSubtotal() {
        double total = 0;

        for (OrderDetail detail : orderDetails) {
            total += detail.calculateAmount();
        }

        return total;
    }

    public double calculateDiscount() {
        return customer.calculateDiscount(calculateSubtotal());
    }

    public double calculateFinalAmount() {
        return calculateSubtotal() - calculateDiscount();
    }

    public void completeOrder() {
        if (completed) {
            throw new IllegalStateException(
                    "This order has already been completed."
            );
        }
        if (orderDetails.isEmpty()) {
            throw new IllegalStateException(
                    "An order must contain at least one product."
            );
        }

        for (OrderDetail detail : orderDetails) {
            if (detail.getQuantity() > detail.getProduct().getQuantity()) {
                throw new IllegalStateException(
                        "Not enough stock for "
                        + detail.getProduct().getProductName()
                );
            }
        }

        for (OrderDetail detail : orderDetails) {
            detail.getProduct().reduceStock(detail.getQuantity());
        }

        completed = true;
    }
    public String toCSV() {
        return transactionId + "," + date + "," + customer.getCustomerID() + "," + calculateFinalAmount();
    }
}
