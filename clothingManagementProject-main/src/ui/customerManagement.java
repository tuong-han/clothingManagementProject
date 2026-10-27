/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui;
import entities.Customer;
import entities.CustomerFactory;
import java.util.*;
import util.DataInput;
import util.Datavalidation;
import service.CustomerService;
/**
 *
 * @author Thanh Dat
 */
public class customerManagement {
private final CustomerService customerService;
 
    public customerManagement(CustomerService customerService) throws Exception {
        this.customerService = customerService;
    }
    //---------------------------------------------------
 
    public void print(List<Customer> customers) throws Exception {
        System.out.println(String.join("", Collections.nCopies(85, "-")));
        System.out.format("%-12s | %-18s | %-18s | %-12s | %-14s%n",
                "Customer ID", "Customer Name", "Address", "Phone", "Type");
        System.out.println(String.join("", Collections.nCopies(85, "-")));
        for (Customer customer : customers) {
            System.out.format("%-12d | %-18s | %-18s | %-12s | %-14s%n",
                    customer.getCustomerID(), customer.getCustomerName(),
                    customer.getAddress(), customer.getPhone(),
                    customer.getType());
        }
        System.out.println(String.join("", Collections.nCopies(85, "-")));
    }
 
    //---------------------------------------------------
 
    // Nhập lại đến khi hợp lệ
    private String inputPhone(String message) {
        while (true) {
            String phone = DataInput.getString(message);
            if (Customer.isValidPhone(phone)) {
                return phone;
            }
            System.out.println("Phone invalid (10 digits, start with 03x/05x/07x/08x/09x). Try again.");
        }
    }
 
    private String inputType(String message) {
        while (true) {
            String type = DataInput.getString(message);
            if (CustomerFactory.isValidType(type)) {
                return type.trim();
            }
            System.out.println("Type must be Regular or VIP. Try again.");
        }
    }
 private String inputRequired(String message, String fieldName) {
    while (true) {
        String value = DataInput.getString(message).trim();
        if (!value.isEmpty()) {
            return value;
        }
        System.out.println(fieldName + " must not be empty. Try again.");
    }
}
    //---------------------------------------------------
 
    public void printCustomerListFromFile() throws Exception {
        List<Customer> customers = customerService.getAllCustomersFromFile();
        print(customers);
    }
    //---------------------------------------------------
 
    public void printCustomerList() throws Exception {
        List<Customer> customers = customerService.getAllCustomers();
        if (customers.isEmpty()) {
            System.out.println("Customer list is empty.");
            return;
        }
        print(customers);
    }
    //---------------------------------------------------
 
    public void addCustomer() {
        try {
            int customerID = DataInput.getIntegerNumber("Enter customer ID: ");
            String customerName = inputRequired("Enter customer name: ", "Name");
            String address= inputRequired("Enter address: ", "Address");
            String phone = inputPhone("Enter phone: ");
            String type = inputType("Enter type (Regular/VIP): ");
 
            Customer customer = CustomerFactory.create(type, customerID, customerName, phone, address);
            if (customerService.addCustomer(customer) == false) {
                System.out.println("Customer already exists.");
            } else {
                System.out.println("Customer has added successfully.");
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    //---------------------------------------------------
 
    public void removeCustomer() {
        try {
            int customerID = DataInput.getIntegerNumber("Enter customer ID: ");
            if (customerService.removeCustomer(customerID) == true) {
                System.out.println("Customer has removed successfully.");
            } else {
                System.out.println("Customer not found.");
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    //---------------------------------------------------
 
    public void updateCustomer() {
        try {
            int customerID = DataInput.getIntegerNumber("Enter customer ID: ");
            Optional<Customer> optional = customerService.getCustomerById(customerID);
            if (!optional.isPresent()) {
                System.out.println("Customer not found.");
                return;
            }
            Customer customer = optional.get();
 
            // Nhấn Enter để giữ giá trị cũ
            String customerName = DataInput.getString("Enter customer name: ").trim();
            if (DataValidation.checkStringEmpty(customerName)) {   
            customer.setCustomerName(customerName);
            }
            String address = DataInput.getString("Enter address: ");
            if (Datavalidation.checkStringEmpty(address)) {
                customer.setAddress(address);
            }
            String phone = DataInput.getString("Enter phone: ");
            if (Datavalidation.checkStringEmpty(phone)) {
                customer.setPhone(phone);   // sai định dạng -> setter ném lỗi -> catch in thông báo
            }
 
            if (customerService.updateCustomer(customer) == true) {
                System.out.println("Customer has updated successfully.");
            } else {
                System.out.println("Customer not found.");
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    //---------------------------------------------------
 
    public void findCustomerById() {
        try {
            int customerID = DataInput.getIntegerNumber("Enter customer ID: ");
            Optional<Customer> customer = customerService.getCustomerById(customerID);
            if (customer.isPresent()) {
                print(Collections.singletonList(customer.get()));
            } else {
                System.out.println("Customer not found.");
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    //---------------------------------------------------
 
    public void checkCustomerIsExist() {
        try {
            int customerID = DataInput.getIntegerNumber("Enter customer ID: ");
            Optional<Customer> customer = customerService.getCustomerById(customerID);
            if (customer.isPresent()) {
                System.out.println("The customer already existed.");
            } else {
                System.out.println("The customer doesn't exist.");
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    //---------------------------------------------------
 
    public void findCustomerByName() {
        try {
            String keyword = DataInput.getString("Enter name: ");
            List<Customer> customers = customerService.searchByName(keyword);
            if (customers.isEmpty()) {
                System.out.println("Not found.");
            } else {
                print(customers);
                System.out.format("%d customer(s) found.%n", customers.size());
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    //---------------------------------------------------
 
    public void findCustomerByType() {
        try {
            String type = inputType("Enter type (Regular/VIP): ");
            List<Customer> customers = customerService.searchByType(type);
            if (customers.isEmpty()) {
                System.out.println("Not found.");
            } else {
                print(customers);
                System.out.format("%d customer(s) found.%n", customers.size());
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    //---------------------------------------------------
 
    public void calculateDiscountedPrice() {
        try {
            int customerID = DataInput.getIntegerNumber("Enter customer ID: ");
            double price = Double.parseDouble(DataInput.getString("Enter price: ").trim());
            double result = customerService.calculateDiscountedPrice(customerID, price);
            System.out.format("Original: %.2f | After discount: %.2f%n", price, result);
        } catch (NumberFormatException ex) {
            System.out.println("Price invalid.");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    //---------------------------------------------------
 
    public void saveCustomers() {
        try {
            customerService.saveCustomersToFile();
            System.out.println("Customers saved.");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
 
}
