/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;
import entities.Customer;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
/**
 *
 * @author Thanh Dat
 */
public class Customerrepositoryimpl implements Customerrepository{
    private final List<Customer> customerList = new ArrayList<>();
    private final FileCustomerDataSource dataSource;
 
    public Customerrepositoryimpl(String fileName) throws Exception {
        dataSource = new FileCustomerDataSource(fileName);
    }
 
    @Override
    public void load() throws Exception {
        customerList.clear();
        customerList.addAll(dataSource.readCustomers());
    }
 
    @Override
    public void save() throws Exception {
        dataSource.writeCustomers(customerList);
    }
 
    @Override
    public List<Customer> findAll() {
        return customerList;
    }
 
    @Override
    public Optional<Customer> findById(int customerID) {
        return customerList.stream()
                .filter(customer -> customer.getCustomerID() == customerID)
                .findFirst();
    }
 
    @Override
    public List<Customer> find(Predicate<Customer> condition) {
        return customerList.stream().filter(condition)
                .collect(Collectors.toList());
    }
 
    @Override
    public boolean add(Customer customer) {
 
        if (customer == null || exists(customer.getCustomerID())) {
            return false;
        }
 
        return customerList.add(customer);
    }
 
    @Override
    public boolean update(Customer customer) {
 
        Optional<Customer> optionalCustomer = findById(customer.getCustomerID());
 
        if (!optionalCustomer.isPresent()) {
            return false;
        }
 
        int index = customerList.indexOf(optionalCustomer.get());
 
        customerList.set(index, customer);
 
        return true;
    }
 
    @Override
    public boolean delete(int customerID) {
        return customerList.removeIf(customer -> customer.getCustomerID() == customerID);
    }
 
    public boolean exists(int customerID) {
        return customerList.stream().anyMatch(customer -> customer.getCustomerID() == customerID);
    }
}
