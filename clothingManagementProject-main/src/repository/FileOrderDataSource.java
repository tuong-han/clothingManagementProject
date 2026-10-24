package repository;

import entities.Customer;
import entities.Order;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import util.FileManager;

public class FileOrderDataSource {
    private final FileManager fileManager;

    public FileOrderDataSource(String fileName) {
        fileManager = new FileManager(fileName);
    }

    public List<Order> readOrders(List<Customer> customers) throws Exception {
        List<Order> orderList = new ArrayList<>();
        List<String> data = fileManager.readDataFromFile();
        int lineNumber = 0;
        
        for (String line : data) {
            lineNumber++;
            if (line == null || line.trim().isEmpty()) {
                continue;
            }
            try {
                List<String> orderData = Arrays.asList(line.split(","));
                String transactionId = orderData.get(0).trim();
                String date = orderData.get(1).trim();
                int customerId = Integer.parseInt(orderData.get(2).trim());
                
                Customer foundCustomer = null;
                for (Customer c : customers) {
                    if (c.getCustomerID() == customerId) {
                        foundCustomer = c;
                        break;
                    }
                }

                if (foundCustomer != null) {
                    Order order = new Order(transactionId, date, foundCustomer);
                    orderList.add(order);
                }
            } catch (RuntimeException ex) {
                throw new Exception("Invalid order data at line " + lineNumber + ".", ex);
            }
        }
        return orderList;
    }

    public void writeOrders(List<Order> orders) throws Exception {
        try {
            List<String> lines = orders.stream()
                    .map(Order::toCSV)
                    .collect(Collectors.toList());
            fileManager.saveDataToFile(String.join(System.lineSeparator(), lines));
        } catch (Exception ex) {
            throw new Exception("Cannot save order data.");
        }
    }
}
