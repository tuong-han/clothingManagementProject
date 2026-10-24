package service;

import entities.Customer;
import entities.Order;
import entities.OrderDetail;
import entities.Product;
import repository.OrderRepository;
import repository.ProductRepository;

/**
 * SalesService handles the business logic for creating and processing sales transactions.
 */
public class SalesService {
    // Private attributes
    private ProductRepository productRepo;
    private OrderRepository orderRepo;

    // Public methods
    public SalesService(ProductRepository productRepo, OrderRepository orderRepo) {
        this.productRepo = productRepo;
        this.orderRepo = orderRepo;
    }

    public Order createTransaction(String transactionId, String date, Customer customer) {
        return new Order(transactionId, date, customer);
    }

    public boolean addProductToTransaction(Order order, String productId, int quantity) {
        if (order == null || quantity <= 0) {
            return false;
        }

        Product product = productRepo.findById(productId).orElse(null);
        if (product == null) {
            System.out.println("Không tìm thấy sản phẩm với ID: " + productId);
            return false;
        }

        if (product.getQuantity() < quantity) {
            System.out.println("Sản phẩm " + product.getProductName()
                    + " không đủ số lượng trong kho!");
            return false;
        }

        try {
            order.addOrderDetail(new OrderDetail(product, quantity));
            return true;
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
            return false;
   
        }
    }

    public boolean confirmSale(Order order) {
        if (order == null || order.getOrderDetails().isEmpty()) {
            System.out.println("Giao dịch không hợp lệ hoặc danh sách sản phẩm rỗng!");
            return false;
        }
        // Trừ sản phẩm trong kho và hoàn thành đơn hàng
        order.completeOrder();
        orderRepo.saveOrder(order);
        System.out.println("Xác nhận thanh toán thành công cho đơn hàng: " + order.getTransactionId());
        return true;
    }
}
