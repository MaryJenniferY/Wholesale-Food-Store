import java.util.*;
import java.sql.*; 

/**
 * This class represents a Data Access Object (DAO) for the Customer entity.
 * It provides methods to perform CRUD operations (Create, Read, Update, Delete) on the Customer entity.
 * The DAO uses a DatabaseConnector to establish a connection with the database.
 */
public class CustomerDAO {
    
    //Establishing a connection with the database

    DatabaseConnector connector = new DatabaseConnector();

    // CRUD operations

    /**
    * This method adds a new customer to the database.
    * 
    * @param customer the Customer object to be added
    * @return true if the customer was added successfully, false otherwise
    * @throws SQLException if there is an issue with the database connection
    */
    public boolean addCustomer(Customer customer) throws SQLException { 
        PreparedStatement statement = null;
        Connection connection = null;
        boolean success = false;
        String query = "INSERT INTO user (businessName, addressLine1, addressLine2, addressLine3, country, postCode, teleNumber) VALUES (?,?,?,?,?,?,?);";

        try{
            connection = connector.getDBConnection();
            statement = connection.prepareStatement(query);

            statement.setString(1, customer.getBusinessName());
            setAddressParameters(statement, 2, customer.getAddress());
            statement.setString(7, customer.getTeleNumber());
            
            int rowsAffected = statement.executeUpdate();
            success =  rowsAffected > 0;

            System.out.println("Customer Added");

            return success;
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
            System.out.println("Customer NOT Added");
            return success;
        } finally {
            connector.closeConnection(connection);
        }

    }

    /**
    * This method retrieves all customers from the database.
    * 
    * @return an ArrayList of Customer objects representing all customers in the database
    * @throws SQLException if there is an issue with the database connection
    */
    public ArrayList<Customer> findAllCustomers() throws SQLException {
        ArrayList<Customer> customers = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;

        String query = "SELECT * FROM user;";

        connection = connector.getDBConnection();
        statement = connection.prepareStatement(query);
        

        try{
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int customerID = resultSet.getInt("customerID");
                String businessName = resultSet.getString("businessName");
                Address address = new Address(
                        resultSet.getString("addressLine1"),
                        resultSet.getString("addressLine2"),
                        resultSet.getString("addressLine3"),
                        resultSet.getString("country"),
                        resultSet.getString("postCode")
                );
                String teleNumber = resultSet.getString("teleNumber");

                // Create customer object and add to the list
                Customer customer = new Customer(customerID, businessName, address, teleNumber);
                customers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connector.closeConnection(connection);
        }

        return customers;
    }

    /**
    * This method retrieves a single customer from the database by their ID.
    * 
    * @param c_id the ID of the customer to retrieve
    * @return a Customer object representing the retrieved customer, or null if no customer with the given ID exists
    * @throws SQLException if there is an issue with the database connection
    */
    public Customer findCustomer(int c_id) throws SQLException {
        Customer b_customer = null;
        Connection connection = null;
        PreparedStatement statement = null;

        String query = "SELECT * FROM user WHERE customerID = ?;";

        connection = connector.getDBConnection();
        statement = connection.prepareStatement(query);

        try {
            statement.setInt(1, c_id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int customerID = resultSet.getInt("customerID");
                String businessName = resultSet.getString("businessName");
                Address address = new Address(
                        resultSet.getString("addressLine1"),
                        resultSet.getString("addressLine2"),
                        resultSet.getString("addressLine3"),
                        resultSet.getString("country"),
                        resultSet.getString("postCode")
                );
                String teleNumber = resultSet.getString("teleNumber");

                // Create Customer object and add to the list
                b_customer = new Customer(customerID, businessName, address, teleNumber);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            connector.closeConnection(connection);
        }

        return b_customer;
    }

    /**
    * This method updates a customer in the database.
    * 
    * @param updatedCustomer the Customer object containing the updated details
    * @return true if the customer was updated successfully, false otherwise
    * @throws SQLException if there is an issue with the database connection
    */
    public boolean updateCustomer(Customer updatedCustomer) throws SQLException {
        boolean success = false;
        Connection connection = null;
        PreparedStatement statement = null;

        String query = "UPDATE user SET businessName = ?, addressLine1 = ?, addressLine2 = ?, addressLine3 = ?, country = ?, postCode = ?, teleNumber = ? WHERE customerID = ?;";

        connection = connector.getDBConnection();
        statement = connection.prepareStatement(query);

        try {
            
            statement.setString(1, updatedCustomer.getBusinessName());
            setAddressParameters(statement, 2, updatedCustomer.getAddress());
            statement.setString(7, updatedCustomer.getTeleNumber());
            statement.setInt(8, updatedCustomer.getCustomerID());

                                 
            int rowsAffected = statement.executeUpdate();
            success = rowsAffected > 0;

            if (success) {
                System.out.println("Customer Updated");
            } else {
                System.out.println("No Customer with the given ID found");
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        } finally {
            connector.closeConnection(connection);
        }

        return success;
    }

    /**
    * This method deletes a customer from the database by their ID.
    * 
    * @param c_Id the ID of the customer to delete
    * @return true if the customer was deleted successfully, false otherwise
    * @throws SQLException if there is an issue with the database connection
    */
    public boolean deleteCustomer(int c_Id) throws SQLException {
        boolean success = false;
        Connection connection = null;
        PreparedStatement statement = null;
    
        String deleteQuery = "DELETE FROM user WHERE customerID = ? ;";
     
        try {
            connection = connector.getDBConnection();
            statement = connection.prepareStatement(deleteQuery);
     
            // Set value for the parameter in the prepared statement
            statement.setInt(1, c_Id);
     
            int rowsAffected = statement.executeUpdate();
            success = rowsAffected > 0;
            
            if(success){
                System.out.println("Deleted Customer with ID: " + c_Id + " successfully.");
            }else{
                System.out.println("Customer not Deleted");
            }
            
        } catch (SQLException e) {
            System.out.println("Delete customer error: " + e);
        }finally {
            connector.closeConnection(connection);
        }
        return success;
    }

    /**
    * This method sets the address parameters for a PreparedStatement.
    * 
    * @param statement the PreparedStatement to set the address parameters for
    * @param startIndex the starting index in the PreparedStatement to set the address parameters
    * @param address the Address object containing the address parameters
    * @throws SQLException if there is an issue setting the parameters
    */
    private void setAddressParameters(PreparedStatement statement, int startIndex, Address address) throws SQLException {
        statement.setString(startIndex , address.getAddressLine1());
        statement.setString(startIndex + 1, address.getAddressLine2());
        statement.setString(startIndex + 2, address.getAddressLine3());
        statement.setString(startIndex + 3, address.getCountry());
        statement.setString(startIndex + 4, address.getPostCode());
    }


}
