/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;
import entities.Customer;
import entities.CustomerFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import util.FileManager;
/**
 *
 * @author Thanh Dat
 */
public class FileCustomerDataSource {
    private final FileManager fileManager;
 
    public FileCustomerDataSource(String fileName) {
        fileManager = new FileManager(fileName);
    }
 
    public List<Customer> readCustomers() throws Exception {
        List<Customer> customerList = new ArrayList<>();
 
        try {
            List<String> data = fileManager.readDataFromFile();
 
            for (String line : data) {
 
                if (line == null || line.trim().isEmpty()) {
                    continue;
                }
 
                List<String> customerData = Arrays.asList(line.split(","));
 
                String type = customerData.get(0).trim();
                int customerID = Integer.parseInt(customerData.get(1).trim());
                String customerName = customerData.get(2).trim();
                String phone = customerData.get(3).trim();
                String address = customerData.get(4).trim();
 
                customerList.add(CustomerFactory.create(type, customerID, customerName, phone, address));
            }
 
        } catch (Exception ex) {
            throw new Exception("Cannot read customer data from file.");
        }
        return customerList;
    }
 
    public void writeCustomers(List<Customer> customers) throws Exception {
        try {
 
            List<String> lines = customers.stream()
                    .map(Customer::toCSV)
                    .collect(Collectors.toList());
 
            String data = String.join("\n", lines);
 
            fileManager.saveDataToFile(data);
 
        } catch (Exception ex) {
            throw new Exception("Cannot save customer data.");
        }
    }
}
