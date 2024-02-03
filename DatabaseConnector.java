import java.sql.*;

/**
 * This class provides functionality to connect and disconnect from a SQLite database.
 */
public class DatabaseConnector {

    /**
    * Represents the connection to the SQLite database.
    */
    Connection connection = null;

    /**
    * Establishes a connection to the SQLite database.
    *
    * @return the established connection
    */
    public Connection  getDBConnection(){
        
        try {
            // Load the SQLite JDBC driver
            Class.forName("org.sqlite.JDBC");          
        }
        catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
        }
        try{
            // Set the database URL
            String dbURL = "jdbc:sqlite:FoodStore.db";
            connection = DriverManager.getConnection(dbURL);
            
            return connection;
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Not Connected");
        }
        return connection;
   
    }

    /**
    * Closes the connection to the SQLite database.
    *
    * @param connection the connection to close
    */
    public void closeConnection(Connection connection) {
        Runnable closeConnection = () -> {
            try {
                // Close the connection if it is not already closed
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace(); 
            }
        };

        closeConnection.run();
    }

}
