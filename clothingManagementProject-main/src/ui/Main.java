package ui;

import entities.Customer;
import entities.Order;
import entities.Product;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import repository.Customerrepository;
import repository.Customerrepositoryimpl;
import repository.OrderRepository;
import repository.ProductRepository;
import repository.ProductRepositoryImpl;
import service.CustomerService;
import service.CustomerServiceImpl;
import service.ProductService;
import service.ProductServiceImpl;
import service.ReportService;
import service.SalesService;
import util.DataInput;
import repository.FileOrderDataSource;

public class Main {
    private static final String CUSTOMER_FILE = "customers.txt";
    private static final String PRODUCT_FILE = "products.txt";
    private static final String ORDER_FILE = "orders.txt";

    private final CustomerService customerService;
    private final ProductService productService;
    private final customerManagement customers;
    private final ProductManagement products;
    private final OrderRepository orderRepository;
    private final SalesService salesService;
    private final ReportService reportService;
    private final FileOrderDataSource fileOrderDataSource;
    private final SalesManagement salesManagement;
    private final ReportManagement reportManagement;

    private Main(CustomerService customerService, ProductRepository productRepository,
                 ProductService productService,
                 OrderRepository orderRepository) throws Exception {
        this.customerService = customerService;
        this.productService = productService;
        this.customers = new customerManagement(customerService);
        this.products = new ProductManagement(productService);
        this.orderRepository = orderRepository;
        this.fileOrderDataSource = new FileOrderDataSource(ORDER_FILE);
        this.salesService = new SalesService(productRepository, orderRepository);
        this.reportService = new ReportService(orderRepository, productRepository);
        this.salesManagement = new SalesManagement(customerService, productService, orderRepository, salesService, fileOrderDataSource);
        this.reportManagement = new ReportManagement(reportService);
        
    }

    public static void main(String[] args) {
        try {
            Customerrepository customerRepository =
                    new Customerrepositoryimpl(CUSTOMER_FILE);
            try {
                customerRepository.load();
            } catch (Exception ex) {
                System.err.println("Warning: could not load customers from "
                        + CUSTOMER_FILE + ": " + ex.getMessage());
                System.err.println("Continuing with an empty customer list.");
            }
            CustomerService customerService =
                    new CustomerServiceImpl(customerRepository);

            ProductRepositoryImpl productRepository =
                    new ProductRepositoryImpl(PRODUCT_FILE);
            try {
                productRepository.loadProductsFromFile();
            } catch (Exception ex) {
                System.err.println("Warning: could not load products from "
                        + PRODUCT_FILE + ": " + ex.getMessage());
                System.err.println("Continuing with an empty product list.");
            }
            ProductService productService = new ProductServiceImpl(productRepository);

            Main app = new Main(customerService, productRepository, productService,
                    new OrderRepository());
            app.run();
        } catch (Exception ex) {
            System.err.println("Cannot start application: " + ex.getMessage());
        }
    }
    // Main Menu
    private void run() {
        while (true) {
            System.out.println("\n===== CLOTHING STORE MANAGEMENT =====");
            System.out.println("1. Manage Products");
            System.out.println("2. Manage Customers");
            System.out.println("3. Sales Management");
            System.out.println("4. Inventory Management");
            System.out.println("5. Reports");
            System.out.println("6. Exit");
            try {
                int choice = DataInput.getIntegerNumber("Choose: ");
                switch (choice) {
                    case 1:
                        productMenu();
                        break;
                    case 2:
                        customerMenu();
                        break;
                    case 3:
                        salesManagement.salesMenu();
                        break;
                    case 4:
                        inventoryMenu();
                        break;
                    case 5:
                        reportsMenu();
                        break;
                    case 6:
                        saveAllData();
                        return;
                    default:
                        System.out.println("Invalid choice.");
                }
            } catch (Exception ex) {
                System.out.println("Operation failed: " + ex.getMessage());
            }
        }
    }

    // Sub Menu
    private void productMenu() {
        while (true) {
            System.out.println("\n===== MANAGE PRODUCTS =====");
            System.out.println("1. List products");
            System.out.println("2. Add product");
            System.out.println("3. Update product");
            System.out.println("4. Remove product");
            System.out.println("5. Search products");
            System.out.println("6. Reload products from file");
            System.out.println("7. Save products");
            System.out.println("0. Back");
            try {
                int choice = DataInput.getIntegerNumber("Choose: ");
                switch (choice) {
                    case 1:
                        products.printAllProducts();
                        break;
                    case 2:
                        products.addProduct();
                        break;
                    case 3:
                        products.updateProduct();
                        break;
                    case 4:
                        products.removeProduct();
                        break;
                    case 5:
                        products.searchProducts();
                        break;
                    case 6:
                        products.printProductListFromFile();
                        break;
                    case 7:
                        productService.saveProductsToFile();
                        System.out.println("Products saved.");
                        break;
                    case 0:
                        return;
                    default:
                        System.out.println("Invalid choice.");
                }
            } catch (Exception ex) {
                System.out.println("Operation failed: " + ex.getMessage());
            }
        }
    }

    private void customerMenu() {
        while (true) {
            System.out.println("\n===== CUSTOMER MANAGEMENT =====");
            System.out.println("1. List customers");
            System.out.println("2. Add customer");
            System.out.println("3. Remove customer");
            System.out.println("4. Update customer");
            System.out.println("5. Find customer by ID");
            System.out.println("6. Find customer by name");
            System.out.println("7. Find customers by type");
            System.out.println("8. Check customer ID");
            System.out.println("9. Reload customers from file");
            System.out.println("10. Save customers");
            System.out.println("11. Calculate discounted price");
            System.out.println("0. Back");
            try {
                int choice = DataInput.getIntegerNumber("Choose: ");
                switch (choice) {
                    case 1: customers.printCustomerList(); break;
                    case 2: customers.addCustomer(); break;
                    case 3: customers.removeCustomer(); break;
                    case 4: customers.updateCustomer(); break;
                    case 5: customers.findCustomerById(); break;
                    case 6: customers.findCustomerByName(); break;
                    case 7: customers.findCustomerByType(); break;
                    case 8: customers.checkCustomerIsExist(); break;
                    case 9: customers.printCustomerListFromFile(); break;
                    case 10: customers.saveCustomers(); break;
                    case 11: customers.calculateDiscountedPrice(); break;
                    case 0: return;
                    default: System.out.println("Invalid choice.");
                }
            } catch (Exception ex) {
                System.out.println("Operation failed: " + ex.getMessage());
            }
        }
    }
    
    private void inventoryMenu() {
        while (true) {
            System.out.println("\n===== INVENTORY MANAGEMENT =====");
            System.out.println("1. View current inventory");
            System.out.println("2. View low-stock products");
            System.out.println("3. Adjust product stock");
            System.out.println("0. Back");
            try {
                int choice = DataInput.getIntegerNumber("Choose: ");
                switch (choice) {
                    case 1:
                        products.printAllProducts();
                        break;
                    case 2:
                        printLowStockProducts();
                        break;
                    case 3:
                        adjustProductStock();
                        break;
                    case 0:
                        return;
                    default:
                        System.out.println("Invalid choice.");
                }
            } catch (Exception ex) {
                System.out.println("Operation failed: " + ex.getMessage());
            }
        }
    }

    private void reportsMenu() {
        while (true) {
            System.out.println("\n===== REPORTS =====");
            System.out.println("1. Monthly revenue");
            System.out.println("2. Best-selling products");
            System.out.println("3. Highest-spending customers");
            System.out.println("0. Back");
            try {
                int choice = DataInput.getIntegerNumber("Choose: ");
                switch (choice) {
                    case 1:
                        reportManagement.monthlyRevenueReport();
                        break;
                    case 2:
                        reportManagement.printBestSellingProducts();
                        break;
                    case 3:
                        reportManagement.printHighestSpendingCustomers();
                        break;
                    case 0:
                        return;
                    default:
                        System.out.println("Invalid choice.");
                }
            } catch (Exception ex) {
                System.out.println("Operation failed: " + ex.getMessage());
            }
        }
    }

    // 4. Inventory Management
    private void printLowStockProducts() {
        boolean found = false;
        for (Product product : productService.getAllProducts()) {
            if (product.isLowStock()) {
                System.out.println(product);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No low-stock products.");
        }
    }

    private void adjustProductStock() throws Exception {
        String productId = DataInput.getString("Product ID: ").trim();
        Optional<Product> product = productService.findById(productId);
        if (!product.isPresent()) {
            System.out.println("Product not found.");
            return;
        }

        int quantity = DataInput.getIntegerNumber("New stock quantity: ");
        product.get().setQuantity(quantity);
        if (productService.updateProduct(product.get())) {
            productService.saveProductsToFile();
            System.out.println("Stock updated and saved.");
        } else {
            System.out.println("Could not update stock.");
        }
    }


    // 5. Report Management
  

    // Save Data when closing main program
    private void saveAllData() throws Exception {
        customerService.saveCustomersToFile();
        productService.saveProductsToFile();
        System.out.println("Customers and products saved.");
    }

}
