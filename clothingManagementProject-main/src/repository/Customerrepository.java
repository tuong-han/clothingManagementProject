/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;
import entities.Customer;
import java.util.*;
import java.util.function.Predicate;
/**
 *
 * @author Thanh Dat
 */
public interface Customerrepository {
    List<Customer> findAll();
 
    Optional<Customer> findById(int customerID);
 
    List<Customer> find(Predicate<Customer> condition);
 
    boolean add(Customer customer);
 
    boolean update(Customer customer);
 
    boolean delete(int customerID);
 
    void load() throws Exception;
 
    void save() throws Exception;
}
