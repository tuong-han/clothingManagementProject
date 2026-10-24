/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;
import java.util.*;
import entities.Customer;
/**
 *
 * @author Thanh Dat
 */
public interface CustomerService {
    
     boolean addCustomer(Customer customer);
 
    Optional<Customer> getCustomerById(int customerID);
 
    boolean updateCustomer(Customer customer);
 
    boolean removeCustomer(int customerID);
 
    List<Customer> searchByName(String keyword);
 
    List<Customer> searchByType(String type);
 
    double calculateDiscountedPrice(int customerID, double price);
 
    List<Customer> getAllCustomers();
 
    List<Customer> getAllCustomersFromFile() throws Exception;
 
    List<Customer> sortBy(List<Customer> customerList, Comparator<Customer> comparator);
 
    void saveCustomersToFile() throws Exception;
}
