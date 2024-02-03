import java.sql.*;
import java.util.*;

/**
 * This class represents a menu for a food store application.
 * It provides various functionalities such as viewing food products, customers, and food items,
 * adding, updating, and deleting these entities, and listing all of them.
 */
public class menu {
 
    /**
    * Displays the menu and handles user inputs for the operations on food products, customers, and food items.
    * @throws SQLException if database access errors occur or this method is called on a closed result set
    */
    public void menuDisplay() throws SQLException{

        int id, c_id, option1, option2;

        FoodProductDAO FPD = new FoodProductDAO();
        FoodProduct FP = null;

        foodItemDAO FID = new foodItemDAO();
        FoodItem FI = null;

        CustomerDAO BCD = new CustomerDAO();
        Customer BC = null;
    
        try (Scanner scanner = new Scanner(System.in)) {
            do{
                System.out.println("The Food Store");
                System.out.println("Choose from the below options");
                System.out.println("------------------------");
                System.out.println("[1] View Food Products");
                System.out.println("[2] View Customers");
                System.out.println("[3] View Food Items");
                System.out.println("[4] Exit");

                option1 = scanner.nextInt();

                switch (option1) {

                    case 1:

                        do{
                            System.out.println("Food Stock");
                            System.out.println("Choose from the below options");
                            System.out.println("------------------------");
                            System.out.println("[1] List all Products");
                            System.out.println("[2] Search for Product by ID");
                            System.out.println("[3] Add a new Product");
                            System.out.println("[4] Update a Product");
                            System.out.println("[5] Delete a Product by ID");
                            System.out.println("[6] Return to Main Menu");
                
                
                            option2 = scanner.nextInt();
                
                
                            switch (option2) {
                                

                                case 1 :
                                    //System.out.println("Listing all the Products");
                                    
                                    FPD.findAllProducts();

                                    List<FoodProduct> allProducts = FPD.findAllProducts();

                                    // Display the retrieved products
                                    System.out.println("Listing all Food Products:");
                                    for (FoodProduct product : allProducts) {
                                        System.out.println(product);
                                    }


                                    break;

                                case 2:
                                    //System.out.println("Searching the Product");

                                    System.out.println("Enter the Food Product ID to be displayed: ");
                                    id = scanner.nextInt();

                                    FP = FPD.findProduct(id);

                                    if (FP != null) {
                                        System.out.println("Found Product:");
                                        System.out.println(FP);
                                    } else {
                                        System.out.println("Product not found.");
                                    }
                                        
                                    break;

                                case 3:
                                    // System.out.println("Adding the Product");

                                    // Get input for the new product
                                    System.out.println("Enter details for the new product:");

                                    System.out.print("Enter SKU: ");
                                    String sku = scanner.next();

                                    System.out.print("Enter description: ");
                                    String description = scanner.next();

                                    System.out.print("Enter category: ");
                                    String category = scanner.next();

                                    System.out.print("Enter price: ");
                                    int price = scanner.nextInt();

                                    FP = new FoodProduct(0, sku, description, category, price);
                                    FPD.addProduct(FP);

                                    break;

                                case 4:
                                    // System.out.println("Updating the Product");

                                    // Get input for the product to be updated
                                    System.out.print("Enter the ID of the product to be updated: ");
                                    int productID = scanner.nextInt();

                                    System.out.print("Enter new SKU: ");
                                    String newSKU = scanner.next();

                                    System.out.print("Enter new description: ");
                                    String newDescription = scanner.next();

                                    System.out.print("Enter new category: ");
                                    String newCategory = scanner.next();

                                    System.out.print("Enter new price: ");
                                    int newPrice = scanner.nextInt();

                                    FP = new FoodProduct(productID, newSKU, newDescription, newCategory, newPrice);
                                    FPD.updateProduct(FP);
                                
                                    break;

                                case 5:
                                    // System.out.println("Deleting the Product");
                                    
                                    System.out.println("Enter the Food Product ID to be deleted: ");
                                    id = scanner.nextInt();
                                    
                                    FPD.deleteProduct(id);

                                    break;


                                default:
                                    System.out.println("Exiting");
                                    break;

                            }
                
                        }while(option2!=6);
                        
                        break;
                    
                    case 2:

                        do{
                            System.out.println("Customers");
                            System.out.println("Choose from the below options");
                            System.out.println("------------------------");
                            System.out.println("[1] List all Customers");
                            System.out.println("[2] Search for Customer by ID");
                            System.out.println("[3] Add a new Customer");
                            System.out.println("[4] Update a Customer");
                            System.out.println("[5] Delete a Customer by ID");
                            System.out.println("[6] Return to Main Menu");
                
                
                            option2 = scanner.nextInt();
                
                
                            switch (option2) {
                                

                                case 1 :
                                    //System.out.println("Listing all the Customers");
                                    
                                    BCD.findAllCustomers();

                                    List<Customer> allCustomers = BCD.findAllCustomers();

                                    // Display the retrieved products
                                    System.out.println("Listing all the Customers:");
                                    for (Customer customer : allCustomers) {
                                        System.out.println(customer);
                                    }


                                    break;

                                case 2:
                                    //System.out.println("Searching the Customer");

                                    System.out.println("Enter the Customer ID to be displayed: ");
                                    c_id = scanner.nextInt();

                                    BC = BCD.findCustomer(c_id);

                                    if (BC != null) {
                                        System.out.println("Found Customer:");
                                        System.out.println(BC);
                                    } else {
                                        System.out.println("Customer not found.");
                                    }
                                        
                                    break;

                                case 3:
                                    // System.out.println("Adding a Customer");

                                    // Get input for the new customer
                                    System.out.println("Enter details for the new customer:");

                                    System.out.print("Enter Business Name: ");
                                    String businessName = scanner.next();

                                    System.out.print("Enter Address Line 1: ");
                                    String addressLine1 = scanner.next();

                                    System.out.print("Enter Address Line 2: ");
                                    String addressLine2 = scanner.next();

                                    System.out.print("Enter Address Line 3: ");
                                    String addressLine3 = scanner.next();

                                    System.out.print("Enter Country: ");
                                    String country = scanner.next();

                                    System.out.print("Enter Post Code: ");
                                    String postCode = scanner.next();

                                    System.out.print("Enter Telephone Number: ");
                                    String teleNumber = scanner.next();

                                    // Creating a Customer object with the user-input details
                                    Address address = new Address(addressLine1, addressLine2, addressLine3, country, postCode);
                                    BC = new Customer(0, businessName, address, teleNumber);

                                    BCD.addCustomer(BC);

                                    break;

                                case 4:
                                    // System.out.println("Updating the Customer");

                                    // Get input for the customer to be updated
                                    System.out.print("Enter the ID of the customer to be updated: ");
                                    int customerID = scanner.nextInt();

                                    System.out.print("Enter Business Name: ");
                                    String newBusinessName = scanner.next();

                                    System.out.print("Enter Address Line 1: ");
                                    String newAddressLine1 = scanner.next();

                                    System.out.print("Enter Address Line 2: ");
                                    String newAddressLine2 = scanner.next();

                                    System.out.print("Enter Address Line 3: ");
                                    String newAddressLine3 = scanner.next();

                                    System.out.print("Enter Country: ");
                                    String newCountry = scanner.next();

                                    System.out.print("Enter Post Code: ");
                                    String newPostCode = scanner.next();

                                    System.out.print("Enter Telephone Number: ");
                                    String newTeleNumber = scanner.next();

                                    Address newAddress = new Address(newAddressLine1, newAddressLine2, newAddressLine3, newCountry, newPostCode);
                                    BC = new Customer(customerID, newBusinessName, newAddress, newTeleNumber);

                                    BCD.updateCustomer(BC);
                                
                                    break;

                                case 5:
                                    // System.out.println("Deleting the Customer");
                                    
                                    System.out.println("Enter the Customer ID to be deleted: ");
                                    c_id = scanner.nextInt();
                                    
                                    BCD.deleteCustomer(c_id);

                                    break;


                                default:
                                    System.out.println("Exiting");
                                    break;

                            }
                
                        }while(option2!=6);
                        
                        break;
                    
                    case 3:
                        do{
                            System.out.println("Food Items");
                            System.out.println("Choose from the below options");
                            System.out.println("------------------------");
                            System.out.println("[1] Add a new Food Item");
                            System.out.println("[2] List all Food Items");
                            System.out.println("[3] Find Food Item by ID");
                            System.out.println("[4] Delete a Food Item by ID");
                            System.out.println("[5] List all Expired Food Items");
                            System.out.println("[6] Return to Main Menu");

                            option2 = scanner.nextInt();
                
                
                            switch (option2) {

                                case 1:
                                    // Add a new Food Item
                                    System.out.println("Enter details for the new Food Item:");
                                    // Collect input for Food Product
                                    System.out.print("Enter Product ID: ");
                                    int productID = scanner.nextInt();

                                    // Collect input for Food Item
                                    System.out.print("Enter Expiry Date (YYYY-MM-DD): ");
                                    String expiryDate = scanner.next();

                                    FoodProduct newFoodProduct = FPD.findProduct(productID) ;

                                    FI = new FoodItem(0, newFoodProduct, expiryDate);

                                    boolean addSuccess = FID.addFoodItem(FI);

                                    if (addSuccess) {
                                        System.out.println("Food Item Added Successfully.");
                                    } else {
                                        System.out.println("Failed to Add Food Item.");
                                    }
                                    break;

                                case 2:
                                    // List All Food Items
                                    ArrayList<FoodItem> allFoodItems = FID.findAllFoodItems();

                                    // Display the retrieved Food Items
                                    System.out.println("Listing all the Food Items:");
                                    for (FoodItem foodItem : allFoodItems) {
                                        System.out.println(foodItem);
                                    }
                                    break;

                                case 3:
                                    // Find Food Item by ID
                                    System.out.println("Enter the Food Item ID to be displayed:");
                                    int foodItemID = scanner.nextInt();

                                    FoodItem foundFoodItem = FID.findFoodItem(foodItemID);

                                    if (foundFoodItem != null) {
                                        System.out.println("Found Food Item:");
                                        System.out.println(foundFoodItem);
                                    } else {
                                        System.out.println("Food Item not found.");
                                    }
                                    break;

                                case 4:
                                    // Delete Food Item by ID
                                    System.out.println("Enter the Food Item ID to be deleted:");
                                    int deleteFoodItemID = scanner.nextInt();

                                    boolean deleteSuccess = FID.deleteFoodItem(deleteFoodItemID);

                                    if (deleteSuccess) {
                                        System.out.println("Food Item Deleted Successfully.");
                                    } else {
                                        System.out.println("Failed to Delete Food Item.");
                                    }
                                    break;

                                case 5:
                                    // Find list of all expired food items
                                    ArrayList<FoodItem> expiredFoodItems = FID.findExpiredFoodItems();

                                    // Display the retrieved expired Food Items
                                    System.out.println("Listing all the Expired Food Items:");
                                    for (FoodItem expiredFoodItem : expiredFoodItems) {
                                        System.out.println(expiredFoodItem);
                                    }
                                    break;

                                default:
                                    System.out.println("Exiting");
                                    break;
                            }

                        }while (option2!=6);

                        break;
                    
                    default:
                        System.out.println("Exiting");
                        break;
                }

            }while(option1!=4);
        }
        
    
    }
 
 
}