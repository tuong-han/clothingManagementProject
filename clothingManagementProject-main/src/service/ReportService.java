/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import entities.Customer;
import entities.Order;
import entities.OrderDetail;
import entities.Product;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import repository.OrderRepository;
import repository.ProductRepository;

/**
 *
 * @author admin
 */
public class ReportService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public ReportService(OrderRepository orderRepository,
                         ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    public void generateMonthlyReport(int month, int year) {
        if (month < 1 || month > 12 || year < 1) {
            throw new IllegalArgumentException("Month or year is invalid.");
        }
        double revenue = 0;
        for (Order order : orderRepository.findAll()) {
            LocalDate date = parseDate(order.getDate());
            if (date != null && date.getMonthValue() == month
                    && date.getYear() == year) {
                revenue += order.calculateFinalAmount();
            }
        }
        System.out.printf("Revenue for %02d/%d: %.2f%n", month, year, revenue);
    }

    public List<Product> getBestSellingProducts() {
        Map<String, Integer> quantitiesByProduct = new HashMap<>();
        for (Order order : orderRepository.findAll()) {
            if (!order.isCompleted()) {
                continue;
            }
            for (OrderDetail detail : order.getOrderDetails()) {
                String id = detail.getProduct().getProductID();
                Integer previous = quantitiesByProduct.get(id);
                quantitiesByProduct.put(id,
                        (previous == null ? 0 : previous) + detail.getQuantity());
            }
        }
        List<Product> products = new ArrayList<>(productRepository.getAllProducts());
        products.removeIf(product -> !quantitiesByProduct.containsKey(product.getProductID()));
        products.sort(Comparator.comparingInt(
                (Product product) -> quantitiesByProduct.get(product.getProductID()))
                .reversed());
        return products;
    }

    public List<Customer> getHighestSpendingCustomers() {
        Map<Integer, Double> spendingByCustomer = new HashMap<>();
        Map<Integer, Customer> customersById = new HashMap<>();
        for (Order order : orderRepository.findAll()) {
            if (!order.isCompleted()) {
                continue;
            }
            int customerId = order.getCustomer().getCustomerID();
            Double previous = spendingByCustomer.get(customerId);
            spendingByCustomer.put(customerId,
                    (previous == null ? 0.0 : previous) + order.calculateFinalAmount());
            customersById.put(customerId, order.getCustomer());
        }
        List<Customer> customers = new ArrayList<>(customersById.values());
        customers.sort(Comparator.comparingDouble(
                (Customer customer) -> spendingByCustomer.get(customer.getCustomerID()))
                .reversed());
        return customers;
    }

    private LocalDate parseDate(String value) {
        try {
            return LocalDate.parse(value);
        } catch (DateTimeParseException ignored) {
            try {
                return LocalDate.parse(value,
                        java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            } catch (DateTimeParseException invalidDate) {
                return null;
            }
        }
    }
}
