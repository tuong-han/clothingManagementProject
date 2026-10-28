package ui;

import entities.Customer;
import entities.Order;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import repository.OrderRepository;
import repository.FileOrderDataSource;
import service.CustomerService;
import service.ProductService;
import service.SalesService;
import util.DataInput;

public class SalesManagement {
    private final CustomerService customerService;
    private final ProductService productService;
    private final OrderRepository orderRepository;
    private final SalesService salesService;
    private final FileOrderDataSource fileOrderDataSource;

    public SalesManagement(
            CustomerService customerService,
            ProductService productService,
            OrderRepository orderRepository,
            SalesService salesService,
            FileOrderDataSource fileOrderDataSource) {

        this.customerService = customerService;
        this.productService = productService;
        this.orderRepository = orderRepository;
        this.salesService = salesService;
        this.fileOrderDataSource = fileOrderDataSource;
    }

    public void salesMenu() {
        while (true) {
            System.out.println("\n===== SALES MANAGEMENT =====");
            System.out.println("1. Create sale");
            System.out.println("2. List completed sales");
            System.out.println("0. Back");

            try {
                int choice = DataInput.getIntegerNumber("Choose: ");

                switch (choice) {
                    case 1:
                        createSale();
                        break;
                    case 2:
                        printSales();
                        break;
                    case 0:
                        return;
                    default:
                        System.out.println("Invalid choice.");
                }
            } catch (Exception ex) {
                System.out.println("Operation failed: " + ex.getMessage());
            }
        }
    }

    private void createSale() throws Exception {
        String transactionId =
                DataInput.getString("Transaction ID: ").trim();

        if (orderRepository.findById(transactionId) != null) {
            System.out.println("That transaction ID already exists.");
            return;
        }

        LocalDate date =
                DataInput.getDate("Order date (dd/MM/yyyy): ");

        int customerId =
                DataInput.getIntegerNumber("Customer ID: ");

        Optional<Customer> customer =
                customerService.getCustomerById(customerId);

        if (!customer.isPresent()) {
            System.out.println("Customer not found.");
            return;
        }

        Order order = salesService.createTransaction(
                transactionId, date.toString(), customer.get());

        boolean addMore = true;

        while (addMore) {
            String productId =
                    DataInput.getString("Product ID: ").trim();

            int quantity =
                    DataInput.getIntegerNumber("Quantity: ");

            if (!salesService.addProductToTransaction(
                    order, productId, quantity)) {
                continue;
            }

            addMore = DataInput.getString(
                    "Add another product? (Y/N): ")
                    .trim().equalsIgnoreCase("Y");
        }

        if (salesService.confirmSale(order)) {
            productService.saveProductsToFile();

            fileOrderDataSource.writeOrders(
                    orderRepository.findAll());

            System.out.printf(
                    "Subtotal: %.2f | Discount: %.2f | Total: %.2f%n",
                    order.calculateSubtotal(),
                    order.calculateDiscount(),
                    order.calculateFinalAmount());
        }
    }

    private void printSales() {
        List<Order> orders = orderRepository.findAll();

        if (orders.isEmpty()) {
            System.out.println(
                    "No completed sales in this session.");
            return;
        }

        for (Order order : orders) {
            System.out.printf(
                    "%s | %s | customer %d | total %.2f%n",
                    order.getTransactionId(),
                    order.getDate(),
                    order.getCustomer().getCustomerID(),
                    order.calculateFinalAmount());
        }
    }
}
