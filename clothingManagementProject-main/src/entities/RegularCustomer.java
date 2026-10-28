/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

/**
 *
 * @author Thanh Dat
 */
public class RegularCustomer extends Customer {
 
    private static final double DISCOUNT_RATE = 0.0;
 
    public RegularCustomer(int customerID, String customerName, String phone, String address) {
        super(customerID, customerName, phone, address);
    }
 
    @Override
    public double calculateDiscount(double totalAmount) {
        return totalAmount * DISCOUNT_RATE;
    }
 
    @Override
    public String getType() {
        return "REGULAR";
    }
    
}
