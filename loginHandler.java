import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.*;
import java.net.HttpURLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

/**
 * This class handles the HTTP requests for user login.
 * It displays the login form and processes POST requests.
 * It checks the entered credentials against the database and redirects to the admin page if successful.
 */
public class loginHandler implements HttpHandler {

    /**
    * Handles the HTTP request sent.
    * If the request method is POST, it calls handlePost(). Otherwise, it shows the login form.
    *
    * @param he HttpExchange object representing the exchange between client and server
    * @throws IOException if an input or output exception occurs
    */
    public void handle(HttpExchange he) throws IOException {
        
        if ("POST".equals(he.getRequestMethod())) {
            handlePost(he);
        } else {
            showLoginForm(he, null);
        }
    }

    /**
    * Displays the login form to the user.
    * If an error message is passed, it displays the error message on the form.
    *
    * @param he HttpExchange object representing the exchange between client and server
    * @param errorMessage string containing an error message to be displayed on the form
    * @throws IOException if an input or output exception occurs
    */
    private void showLoginForm(HttpExchange he, String errorMessage) throws IOException {

        he.sendResponseHeaders(200, 0);

        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(he.getResponseBody()));

        out.write(
            "<!DOCTYPE html>"+
            "<html>" +
            "<head> <title>Whole Sale Food Product Shop</title> " +
            "<link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css\" crossorigin=\"anonymous\">" +
            "<style>" +
               ".loginBody {" +
                    "font-family: Montserrat, sans-serif;" +
                    "background-image: linear-gradient(to bottom right, #2AF598, #08AEEA);"+
                    "height: 100vh;"+
                    "width: 100%;"+
                    "display: flex;" +
                    "justify-content: center;" +
                    "align-items: center;" +
               "}" +
               ".loginCon{"+
                "padding: 20px 20px ;"+
                "display: flex;"+
                "justify-content: center;"+
                "align-items: center;"+
                "text-align: center;"+
                "flex-direction: column;"+
                "width: 35%;"+
                "height: 70%;"+
                "background-color: rgba(255, 255, 255, 0.7);" + 
                "border-radius: 30px;" + 
               "}"+
                ".loginCon h2{"+
                    "margin-bottom: 50px;"+
                    "margin-top:-40px;"+
                    "font-weight: 600;"+
                "}"+
            "</style>" +
            "</head>" +
            "<body class=\"loginBody\">"+
            "<div class=\"loginCon mt-4\" >" +
            "<h2 class=\"mt-1\">Login To Your Account</h2>");

        // Display error message if the credentials dont match the login details from the database
        if (errorMessage != null) {
            out.write("<p style=\"color: red;\">" + errorMessage + "</p>");
        }

        out.write(
            "<form action=\"/\" method=\"post\">" +
            "<div class=\"form-group\">" +
            "<label for=\"username\">Username:</label>" +
            "<input type=\"text\" class=\"form-control\" id=\"username\" name=\"username\" placeholder=\"Enter your username\">" +
            "</div>" +
            "<div class=\"form-group\">" +
            "<label for=\"password\">Password:</label>" +
            "<input type=\"password\" class=\"form-control\" id=\"password\" name=\"password\" placeholder=\"Enter your password\">" +
            "</div>" +
            "<button type=\"submit\" class=\"btn btn-primary\">Login</button>" +
            "</form>" +
            "</div>" +
            "<script src=\"https://code.jquery.com/jquery-3.5.1.slim.min.js\" integrity=\"sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj\" crossorigin=\"anonymous\"></script>" +
            "<script src=\"https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js\" integrity=\"sha384-1QZx02jA7G/VZj5b4lGv3LU7d0GXy/GRxh8jzZsCg3Si1NJ0uZjvS6+Q/EQd6r3h\" crossorigin=\"anonymous\"></script>" +
            "<script src=\"https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.min.js\" integrity=\"sha384-bCm0HR8gvHbJpG6KecRm9aPez9ImqB4aPBACquX7ZVaQEC0apv8V0a5rJw0EzZB4\" crossorigin=\"anonymous\"></script>" +
            "</body>" +
            "</html>");

        out.close();

        he.getResponseBody().close();
    }

    /**
    * Handles the POST request sent by the user.
    * Retrieves the form parameters, hashes the password, and validates the credentials against the database.
    * If the validation is successful, it redirects to the admin welcome page. Otherwise, it shows the login form with an error message.
    *
    * @param he HttpExchange object representing the exchange between client and server
    * @throws IOException if an input or output exception occurs
    */
    private void handlePost(HttpExchange he) throws IOException {
        // Retrieve form parameters
        InputStreamReader isr = new InputStreamReader(he.getRequestBody(), "utf-8");
        BufferedReader br = new BufferedReader(isr);
        String formData = br.readLine(); 

        // Parse form data to get username and password
        String[] params = formData.split("&");
        String username = params[0].split("=")[1];
        String password = params[1].split("=")[1];

        // MD5 Password Hashing
        String hashedPassword = hashPasswordMD5(password);

        // Check credentials against the database
        if (validateUser(username, hashedPassword)) {
            // Successful login, redirect to adminWelcome
            he.getResponseHeaders().set("Location", "/adminWelcome");
            he.sendResponseHeaders(HttpURLConnection.HTTP_SEE_OTHER, -1);
        } else {
            // Incorrect login, show login form with error message
            showLoginForm(he, "Invalid credentials. Please try again.");
        }

        br.close();
        isr.close();
        he.getResponseBody().close();
    }

     /**
    * Hashes the password using the MD5 algorithm.
    *
    * @param password string containing the plain text password
    * @return string containing the hashed password
    */
    private String hashPasswordMD5(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 algorithm not found", e);
        }
    }

    /**
    * Validates the user credentials against the database.
    * Connects to the database, prepares a SQL statement, and executes it with the provided username and hashed password.
    * If the result set has at least one row, the credentials are valid.
    *
    * @param username string containing the username
    * @param hashedPassword string containing the hashed password
    * @return boolean indicating whether the credentials are valid or not
    */
    private boolean validateUser(String username, String hashedPassword) {
        DatabaseConnector connector = new DatabaseConnector();
        PreparedStatement statement = null;
        Connection connection = null;
        String query = "SELECT * FROM login WHERE userName = ? AND password = ?;";
        try {

            connection = connector.getDBConnection();
            statement = connection.prepareStatement(query); 

            statement.setString(1, username);
            statement.setString(2, hashedPassword);

            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next(); // If the result set has at least one row, credentials are valid
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
