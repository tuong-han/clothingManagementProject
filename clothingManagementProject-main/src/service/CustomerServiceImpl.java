/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;
import java.util.*;
import java.util.stream.Collectors;
import java.util.function.Predicate;
import entities.Customer;
import repository.Customerrepository;
/**
 *
 * @author Thanh Dat
 */
public class CustomerServiceImpl implements CustomerService {
 
    private final Customerrepository repository;
 
    public CustomerServiceImpl(Customerrepository repository) {
        this.repository = repository;
    }
 
    @Override
    public Optional<Customer> getCustomerById(int customerID) {
        return repository.findById(customerID);
    }
 
    @Override
    public boolean addCustomer(Customer customer) {
        return repository.add(customer);
    }
 
    @Override
    public boolean updateCustomer(Customer customer) {
        return repository.update(customer);
    }
 
    @Override
    public List<Customer> searchByName(String keyword) {
 
        if (keyword == null || keyword.trim().isEmpty()) {
            return Collections.emptyList();
        }
        Predicate<Customer> condition = customer -> customer.getCustomerName()
                .toLowerCase().contains(keyword.toLowerCase());
        return repository.find(condition);
    }
 
    @Override
    public List<Customer> searchByType(String type) {
 
        if (type == null || type.trim().isEmpty()) {
            return Collections.emptyList();
        }
        Predicate<Customer> condition = customer -> customer.getType()
                .equalsIgnoreCase(type.trim());
        return repository.find(condition);
    }
 
    @Override
    public double calculateDiscountedPrice(int customerID, double price) {
 
        if (price < 0) {
            throw new IllegalArgumentException("Price must not be negative.");
        }
        Customer customer = repository.findById(customerID)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found."));
 
        return price - customer.calculateDiscount(price);
    }
 
    @Override
    public void saveCustomersToFile() throws Exception {
        repository.save();
    }
 
    @Override
    public boolean removeCustomer(int customerID) {
        return repository.delete(customerID);
    }
 
    @Override
    public List<Customer> getAllCustomers() {
        return repository.findAll();
    }
 
    @Override
    public List<Customer> getAllCustomersFromFile() throws Exception {
        repository.load();
        return repository.findAll();
    }
 
    @Override
    public List<Customer> sortBy(List<Customer> customerList, Comparator<Customer> comparator) {
        return customerList.stream().sorted(comparator)
                .collect(Collectors.toList());
    }
}
