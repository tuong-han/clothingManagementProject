/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;
/**
 *
 * @author Thanh Dat
 */
public abstract class Customer {
       private int customerID;
    private String customerName;
    private String address;
    private String phone;
 
    public Customer() {
    }
 
    public Customer(int customerID, String customerName, String phone, String address) {
        this.customerID = customerID;
        this.customerName = customerName;
        setPhone(phone);              
        this.address = address;
    }
 
    public int getCustomerID() {
        return customerID;
    }
 
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }
 
    public String getCustomerName() {
        return customerName;
    }
 
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
 
    public String getAddress() {
        return address;
    }
 
    public void setAddress(String address) {
        this.address = address;
    }
 
    public String getPhone() {
        return phone;
    }
    
    public static boolean isValidPhone(String phone) {
    return phone != null && phone.matches("(0\\d{9,10}|\\+84\\d{9,10})");
}

    public void setPhone(String phone) {
    if (!isValidPhone(phone)) {
        throw new IllegalArgumentException("Phone Invalid");
    }else{
    this.phone = phone;
    }
}
 
   public String toCSV() {
        return String.format("%s,%d,%s,%s,%s",
                getType(), customerID, customerName, phone, address);
    }
 
    @Override
    public String toString() {
        return toCSV();
    }
    
    public abstract double calculateDiscount(double totalAmount);
    public abstract String getType();
}
