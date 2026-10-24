package ui;

public class ReportManagement {
    private void monthlyRevenueReport() throws Exception {
        int month = DataInput.getIntegerNumber("Month (1-12): ");
        int year = DataInput.getIntegerNumber("Year: ");
        reportService.generateMonthlyReport(month, year);
    }

    private void printBestSellingProducts() {
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

    private void printHighestSpendingCustomers() {
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
