import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * This class represents a Data Access Object (DAO) for FoodItem objects.
 * It provides methods for CRUD operations (Create, Read, Update, Delete) on FoodItem objects in a database.
 */
public class foodItemDAO {
    private DatabaseConnector connector = new DatabaseConnector();

    //CRUD Operations

    /**
    * Adds a new FoodItem to the database.
    *
    * @param foodItem The FoodItem object to be added.
    * @return Returns true if the operation was successful, false otherwise.
    * @throws SQLException if a database access error occurs.
    */
    public boolean addFoodItem(FoodItem foodItem) throws SQLException {
        PreparedStatement statement = null;
        Connection connection = null;
        boolean success = false;

        String query = "INSERT INTO foodItem (productID, SKU, description, category, price, expiryDate) VALUES (?, ?, ?, ?, ?, ?);";

        try {
            connection = connector.getDBConnection();
            statement = connection.prepareStatement(query);

            FoodProduct foodProduct = foodItem.getFoodProduct();

            statement.setInt(1, foodProduct.getID());
            statement.setString(2, foodProduct.getSKU());
            statement.setString(3, foodProduct.getDescription());
            statement.setString(4, foodProduct.getCategory());
            statement.setInt(5, foodProduct.getPrice());
            statement.setString(6, foodItem.getExpiryDate());

            int rowsAffected = statement.executeUpdate();
            success = rowsAffected > 0;

            System.out.println("Food Item Added");

            return success;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Food Item NOT Added");
            return success;
        } finally {
            connector.closeConnection(connection);
        }
    }

    /**
    * Retrieves all FoodItem objects from the database.
    *
    * @return A list of FoodItem objects.
    * @throws SQLException if a database access error occurs.
    */
    public ArrayList<FoodItem> findAllFoodItems() throws SQLException {
        ArrayList<FoodItem> foodItems = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;

        String query = "SELECT * FROM foodItem;";

        connection = connector.getDBConnection();
        statement = connection.prepareStatement(query);

        try {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int foodItemID = resultSet.getInt("foodItemID");
                int productID = resultSet.getInt("productID");
                String SKU = resultSet.getString("SKU");
                String description = resultSet.getString("description");
                String category = resultSet.getString("category");
                int price = resultSet.getInt("price");
                String expiryDate = resultSet.getString("expiryDate");

                FoodProduct foodProduct = new FoodProduct(productID, SKU, description, category, price);

                FoodItem foodItem = new FoodItem(foodItemID, foodProduct, expiryDate);
                foodItems.add(foodItem);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connector.closeConnection(connection);
        }

        return foodItems;
    }

    /**
    * Deletes a FoodItem with a given ID from the database.
    *
    * @param foodItemID The ID of the FoodItem to be deleted.
    * @return Returns true if the operation was successful, false otherwise.
    * @throws SQLException if a database access error occurs.
    */
    public boolean deleteFoodItem(int foodItemID) throws SQLException {
        boolean success = false;
        Connection connection = null;
        PreparedStatement statement = null;

        String query = "DELETE FROM foodItem WHERE foodItemID = ?;";

        try {
            connection = connector.getDBConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, foodItemID);

            int rowsAffected = statement.executeUpdate();
            success = rowsAffected > 0;

            if (success) {
                System.out.println("Deleted Food Item with ID: " + foodItemID + " successfully.");
            } else {
                System.out.println("Food Item not Deleted");
            }
            return success;

        } catch (SQLException e) {
            System.out.println("Delete Food Item error: " + e);
            return success;
        } finally {
            connector.closeConnection(connection);
        }
        
    }

    /**
    * Finds a FoodItem with a given ID in the database.
    *
    * @param foodItemID The ID of the FoodItem to be found.
    * @return The requested FoodItem object, or null if not found.
    * @throws SQLException if a database access error occurs.
    */
    public FoodItem findFoodItem(int foodItemID) throws SQLException {
        FoodItem foodItem = null;
        Connection connection = null;
        PreparedStatement statement = null;

        String query = "SELECT * FROM foodItem WHERE foodItemID = ?;";

        try {
            connection = connector.getDBConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, foodItemID);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int productID = resultSet.getInt("productID");
                String SKU = resultSet.getString("SKU");
                String description = resultSet.getString("description");
                String category = resultSet.getString("category");
                int price = resultSet.getInt("price");
                String expiryDateStr = resultSet.getString("expiryDate");

                // Create FoodProduct object
                FoodProduct foodProduct = new FoodProduct(productID, SKU, description, category, price);

                // Create FoodItem object
                foodItem = new FoodItem(foodItemID, foodProduct, expiryDateStr);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connector.closeConnection(connection);
        }

        return foodItem;
    }

    /**
    * Finds all expired FoodItem objects in the database.
    *
    * @return A list of expired FoodItem objects.
    * @throws SQLException if a database access error occurs.
    */
    public ArrayList<FoodItem> findExpiredFoodItems() throws SQLException {
        ArrayList<FoodItem> expiredFoodItems = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;

        String query = "SELECT * FROM foodItem WHERE expiryDate < ?;";

        try {
            connection = connector.getDBConnection();
            statement = connection.prepareStatement(query);

            // Convert current date to a String in the same format as the expiryDate
            LocalDate currentDate = LocalDate.now();
            String currentDateStr = currentDate.toString();

            statement.setString(1, currentDateStr);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int foodItemID = resultSet.getInt("foodItemID");
                int productID = resultSet.getInt("productID");
                String SKU = resultSet.getString("SKU");
                String description = resultSet.getString("description");
                String category = resultSet.getString("category");
                int price = resultSet.getInt("price");
                String expiryDate = resultSet.getString("expiryDate");

                FoodProduct foodProduct = new FoodProduct(productID, SKU, description, category, price);

                FoodItem expiredFoodItem = new FoodItem(foodItemID, foodProduct, expiryDate);
                expiredFoodItems.add(expiredFoodItem);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connector.closeConnection(connection);
        }

        return expiredFoodItems;
    }

}
