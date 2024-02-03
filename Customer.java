/**
 * This class represents a customer with various attributes such as customerID, businessName, address and teleNumber.
 * Provides methods to get and set these attributes.
 * Also includes a method to print the details of the customer.
 */
public class Customer {
   private int customerID;
   private String businessName;
   private Address address;
   private String teleNumber;
   
   /**
    * Constructor for the Customer class.
    * @param customerID The ID for the customer.
    * @param businessName The name of the business associated with the customer.
    * @param address The address of the customer.
    * @param teleNumber The telephone number of the customer.
    */
   public Customer(int customerID, String businessName, Address address, String teleNumber){
       this.customerID = customerID;
       this.businessName = businessName;
       this.address = address;
       this.teleNumber = teleNumber;
   }

   /**
    * Getter for the customerID attribute.
    * @return The customerID of the customer.
    */
   public int getCustomerID(){
       return customerID;
   }

   /**
    * Getter for the businessName attribute.
    * @return The businessName of the customer.
    */
   public String getBusinessName(){
       return businessName;
   }

   /**
    * Getter for the address attribute.
    * @return The address of the customer.
    */
   public Address getAddress(){
       return address;
   }

   /**
    * Getter for the teleNumber attribute.
    * @return The telephone number of the customer.
    */
   public String getTeleNumber(){
       return teleNumber;
   }

   /**
    * Returns a string representation of the customer's details.
    * @return A string containing the customer's details.
    */
   @Override
   public String toString() {
       return "Customer [Customer ID=" + customerID + ", Business Name=" + businessName + ", Address=" + address + ", Telephone Number=" + teleNumber + "]";
   }
}
