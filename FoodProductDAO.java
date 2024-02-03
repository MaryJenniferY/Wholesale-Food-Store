import java.util.*;
import java.sql.*;

/**
 * This class represents the Data Access Object (DAO) for the FoodProduct entity.
 * It provides methods for performing CRUD (Create, Read, Update, Delete) operations on the FoodProduct data in the database.
 */
public class FoodProductDAO {

    // Establish a connection to the database
    DatabaseConnector connector = new DatabaseConnector();

    // CRUD operations

    /**
    * Adds a new food product to the database.
    * 
    * @param fp The food product to be added.
    * @return True if the product was successfully added, false otherwise.
    * @throws SQLException if there is a problem executing the SQL statement.
    */
    public boolean addProduct(FoodProduct fp) throws SQLException{ 
        PreparedStatement statement = null;
        Connection connection = null;
        boolean success = false;
        String query = "INSERT INTO stock (SKU, description, category, price) VALUES (?,?,?,?);";

        try{
            connection = connector.getDBConnection();
            statement = connection.prepareStatement(query);

            statement.setString(1, fp.getSKU());
            statement.setString(2, fp.getDescription());
            statement.setString(3, fp.getCategory());
            statement.setInt(4, fp.getPrice());

            int rowsAffected = statement.executeUpdate();
            success =  rowsAffected > 0;

            System.out.println("Product Added");

            return success;
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
            System.out.println("Product NOT Added");
            return success;
        } finally {
            connector.closeConnection(connection);
        }

    }

    /**
    * Retrieves all food products from the database.
    * 
    * @return A list of all food products.
    * @throws SQLException if there is a problem executing the SQL statement.
    */
    public ArrayList<FoodProduct> findAllProducts() throws SQLException {
        ArrayList<FoodProduct> products = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;

        String query = "SELECT * FROM stock;";

        connection = connector.getDBConnection();
        statement = connection.prepareStatement(query);
        

        try{
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int productID = resultSet.getInt("productID");
                String SKU = resultSet.getString("SKU");
                String description = resultSet.getString("description");
                String category = resultSet.getString("category");
                int price = resultSet.getInt("price");

                FoodProduct product = new FoodProduct(productID, SKU, description, category, price);
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connector.closeConnection(connection);
        }

        return products;
    }

    /**
    * Retrieves a single food product by its ID from the database.
    * 
    * @param p_id The ID of the product to retrieve.
    * @return The requested food product.
    * @throws SQLException if there is a problem executing the SQL statement.
    */
    public FoodProduct findProduct(int p_id) throws SQLException {
        FoodProduct f_product = null;
        Connection connection = null;
        PreparedStatement statement = null;

        String query = "SELECT * FROM stock WHERE productID = ?;";

        connection = connector.getDBConnection();
        statement = connection.prepareStatement(query);

        try {
            statement.setInt(1, p_id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int productID = resultSet.getInt("productID");
                String SKU = resultSet.getString("SKU");
                String description = resultSet.getString("description");
                String category = resultSet.getString("category");
                int price = resultSet.getInt("price");

                f_product = new FoodProduct(productID, SKU, description, category, price);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            connector.closeConnection(connection);
        }

        return f_product;
    }

    /**
    * Updates a food product in the database.
    * 
    * @param updatedProduct The food product with the updated details.
    * @return True if the product was successfully updated, false otherwise.
    * @throws SQLException if there is a problem executing the SQL statement.
    */
    public boolean updateProduct(FoodProduct updatedProduct) throws SQLException {
        boolean success = false;
        Connection connection = null;
        PreparedStatement statement = null;

        String query = "UPDATE stock SET SKU = ?, description = ?, category = ?, price = ? WHERE productID = ?;";

        connection = connector.getDBConnection();
        statement = connection.prepareStatement(query);

        try {
            statement.setString(1, updatedProduct.getSKU());
            statement.setString(2, updatedProduct.getDescription());
            statement.setString(3, updatedProduct.getCategory());
            statement.setInt(4, updatedProduct.getPrice());
            statement.setInt(5, updatedProduct.getID());

            int rowsAffected = statement.executeUpdate();
            success = rowsAffected > 0;

            if (success) {
                System.out.println("Product Updated");
            } else {
                System.out.println("No Product with the given ID found");
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        } finally {
            connector.closeConnection(connection);
        }

        return success;
    }

    /**
    * Deletes a food product from the database by its ID.
    * 
    * @param p_Id The ID of the product to delete.
    * @return True if the product was successfully deleted, false otherwise.
    */
    public boolean deleteProduct(int p_Id)  {
        boolean success = false;
        Connection connection = null;
        PreparedStatement statement = null;
    
        String query = "DELETE FROM stock WHERE productID = ? ;";
     
        try {
            connection = connector.getDBConnection();
            statement = connection.prepareStatement(query);
     
            statement.setInt(1, p_Id);
     
            int rowsAffected = statement.executeUpdate();
            success = rowsAffected > 0;
            
            if(success){
                System.out.println("Deleted Food Product with ID: " + p_Id + " successfully.");
            }else{
                System.out.println("Product not Deleted");
            }
            
        } catch (SQLException e) {
            System.out.println("Delete Food Product error: " + e);
            
        } finally {
            connector.closeConnection(connection);
        }
        return success;
    }

    /**
    * Retrieves all food products from the database that belong to a certain category.
    * 
    * @param category The category of the products to retrieve.
    * @return A list of all food products that belong to the specified category.
    * @throws SQLException if there is a problem executing the SQL statement.
    */
    public ArrayList<FoodProduct> findProductsByCategory(String category) throws SQLException {
        ArrayList<FoodProduct> products = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
    
        String query = "SELECT * FROM stock WHERE category LIKE ?;";
    
        connection = connector.getDBConnection();
        statement = connection.prepareStatement(query);
    
        try {
            statement.setString(1, category);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int productID = resultSet.getInt("productID");
                String SKU = resultSet.getString("SKU");
                String description = resultSet.getString("description");
                String originalCategory = resultSet.getString("category");
                int price = resultSet.getInt("price");

                FoodProduct product = new FoodProduct(productID, SKU, description, originalCategory, price);
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connector.closeConnection(connection);
        }
    
        return products;
    }

    /**
    * Retrieves all food products from the database that match a certain description.
    * 
    * @param description The description of the products to retrieve.
    * @return A list of all food products that match the specified description.
    * @throws SQLException if there is a problem executing the SQL statement.
    */
    public ArrayList<FoodProduct> findProductsByDescription(String description) throws SQLException {
        ArrayList<FoodProduct> products = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
    
        String query = "SELECT * FROM stock WHERE description LIKE ?;";
    
        connection = connector.getDBConnection();
        statement = connection.prepareStatement(query);
    
        try {
            statement.setString(1, "%" + description + "%");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int productID = resultSet.getInt("productID");
                String SKU = resultSet.getString("SKU");
                String originalDescription = resultSet.getString("description");
                String category = resultSet.getString("category");
                int price = resultSet.getInt("price");

                FoodProduct product = new FoodProduct(productID, SKU, originalDescription, category, price);
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connector.closeConnection(connection);
        }
    
        return products;
    }

}
