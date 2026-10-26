package ui;

import entities.Customer;
import entities.Product;
import service.ReportService;
import util.DataInput;

import java.util.List;

public class ReportManagement {
    private final ReportService reportService;

    public ReportManagement(ReportService reportService) {
        this.reportService = reportService;
    }

    public void monthlyRevenueReport() throws Exception {
        int month = DataInput.getIntegerNumber("Month (1-12): ");
        int year = DataInput.getIntegerNumber("Year: ");
        reportService.generateMonthlyReport(month, year);
    }

    public void printBestSellingProducts() {
        List<Product> bestSellers = reportService.getBestSellingProducts();
        if (bestSellers.isEmpty()) {
            System.out.println("No completed sales in this session.");
            return;
        }
        for (Product product : bestSellers) {
            System.out.println(product.getProductID() + " - "
                    + product.getProductName());
        }
    }

    public void printHighestSpendingCustomers() {
        List<Customer> topCustomers = reportService.getHighestSpendingCustomers();
        if (topCustomers.isEmpty()) {
            System.out.println("No completed sales in this session.");
            return;
        }
        for (Customer customer : topCustomers) {
            System.out.println(customer.getCustomerID() + " - "
                    + customer.getCustomerName());
        }
    }
}
