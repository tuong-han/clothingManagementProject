package repository;

import entities.Customer;
import entities.Order;
import entities.OrderDetail;
import entities.Product;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import util.FileManager;

/**
 * OrderRepository class handles the storage and retrieval of Order objects.
 */
public class OrderRepository {
    private static final String DEFAULT_ORDER_FILE = "orders.txt";
    private static final String DEFAULT_CUSTOMER_FILE = "customers.txt";

    // Private attributes
    private List<Order> orders;
    private final String orderFileName;
    private final FileManager fileManager;
    private final FileCustomerDataSource customerDataSource;

    // Public methods
    public OrderRepository() {
        this(DEFAULT_ORDER_FILE, DEFAULT_CUSTOMER_FILE);
    }

    public OrderRepository(String orderFileName, String customerFileName) {
        this.orders = new ArrayList<>();
        this.orderFileName = orderFileName;
        this.fileManager = new FileManager(orderFileName);
        this.customerDataSource = new FileCustomerDataSource(customerFileName);
        try {
            loadOrdersFromFile();
        } catch (Exception ex) {
            System.err.println("Warning: could not load orders from "
                    + orderFileName + ": " + ex.getMessage());
            System.err.println("Continuing with an empty order list.");
        }
    }

    public void saveOrder(Order order) {
        if (order != null) {
            orders.add(order);
        }
    }

    public List<Order> findAll() {
        return this.orders;
    }

    public Order findById(String id) {
        for (Order order : orders) {
            if (order.getTransactionId().equalsIgnoreCase(id)) {
                return order;
            }
        }
        return null;
    }

    /**
     * Reads orders from the order file (one order per line, as written by
     * Order.toCSV(): transactionId,date,customerId,finalAmount).
     * If the file does not exist yet (first run), the list stays empty.
     * The list is replaced only when every line is valid.
     */
    public void loadOrdersFromFile() throws Exception {
        if (!new File(orderFileName).exists()) {
            return;
        }

        List<Customer> customers = customerDataSource.readCustomers();
        List<String> data = fileManager.readDataFromFile();
        List<Order> loadedOrders = new ArrayList<>();
        int lineNumber = 0;

        for (String line : data) {
            lineNumber++;
            if (line == null || line.trim().isEmpty()) {
                continue;
            }
            try {
                loadedOrders.add(parseOrder(line, customers));
            } catch (RuntimeException ex) {
                throw new Exception("Invalid order data at line " + lineNumber + ".", ex);
            }
        }

        orders.clear();
        orders.addAll(loadedOrders);
    }

    // Private methods

    /**
     * The order file stores only the final amount, not the product list,
     * so each order is rebuilt with one summary detail whose price makes
     * Order.calculateFinalAmount() equal the stored amount.
     */
    private Order parseOrder(String line, List<Customer> customers) {
        String[] orderData = line.split(",");
        String transactionId = orderData[0].trim();
        String date = orderData[1].trim();
        int customerId = Integer.parseInt(orderData[2].trim());
        double finalAmount = Double.parseDouble(orderData[3].trim());

        Customer customer = null;
        for (Customer c : customers) {
            if (c.getCustomerID() == customerId) {
                customer = c;
                break;
            }
        }
        if (customer == null) {
            throw new IllegalArgumentException("Customer not found: " + customerId);
        }

        // subtotal - discount = finalAmount, and discount is proportional to subtotal
        double discountRate = customer.calculateDiscount(1.0);
        double subtotal = Math.round(finalAmount / (1.0 - discountRate) * 100.0) / 100.0;

        Product summary = new Product("ORDER-" + transactionId,
                "Order " + transactionId, "History", "N/A", "N/A", subtotal, 1);

        Order order = new Order(transactionId, date, customer);
        order.addOrderDetail(new OrderDetail(summary, 1));
        order.completeOrder();
        return order;
    }
}
