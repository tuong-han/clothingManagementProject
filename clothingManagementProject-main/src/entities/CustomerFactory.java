/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

/**
 *
 * @author Thanh Dat
 */
public final class CustomerFactory {
    private CustomerFactory() {
    }
 
    public static Customer create(String type, int customerID, String customerName,
                                  String phone, String address) {
        if (type == null) {
            throw new IllegalArgumentException("Customer type must be Regular or VIP.");
        }
        switch (type.trim().toUpperCase()) {
            case "REGULAR":
                return new RegularCustomer(customerID, customerName, phone, address);
            case "VIP":
                return new VIPCustomer(customerID, customerName, phone, address);
            default:
                throw new IllegalArgumentException("Customer type must be Regular or VIP.");
        }
    }
 
    public static boolean isValidType(String type) {
        return type != null && type.trim().matches("(?i)regular|vip");
    }
}
