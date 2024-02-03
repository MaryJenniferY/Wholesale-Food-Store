import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.SQLException;
import java.util.Map;

/**
 * This class handles HTTP requests for adding food items.
 * It parses form data, creates a new food item, adds it to the database, and sends a response.
 * If an error occurs during this process, it sends an error message.
 */
public class FIAProcessHandler implements HttpHandler {

    /**
    * Handles HTTP exchange.
    * Parses form data, creates a new food item, adds it to the database with the help of add method in the DAO class, and sends a response.
    * If an error occurs during this process, it sends an error message.
    *
    * @param he the HTTP exchange
    * @throws IOException if an I/O exception occurs
    */
    @Override
    public void handle(HttpExchange he) throws IOException {
        System.out.println("Food Item Add Process Handler Called");

        he.sendResponseHeaders(200, 0);
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(he.getResponseBody()));

        FoodProduct foodProduct = null;
        boolean itemAdded = false; 

        try {
            // Parse form data
            Map<String, String> formData = Util.parseFormData(he);

            System.out.println("This is the data " + formData);

            // Get details from the user
            int foodProductId = Integer.parseInt(formData.get("productID"));
            String expiryDate = formData.get("expiryDate");

            // Creating new food item with the given details
            foodItemDAO foodItemDAO = new foodItemDAO();
            FoodProductDAO foodProductDAO = new FoodProductDAO();

            // Fetch associated FoodProduct
            foodProduct = foodProductDAO.findProduct(foodProductId);

            if (foodProduct == null) {
                throw new IllegalArgumentException("Food product not found for ID: " + foodProductId);
            }

            FoodItem foodItem = new FoodItem(1, foodProduct, expiryDate);

            // Add the item to the database
            itemAdded = foodItemDAO.addFoodItem(foodItem);

            if (itemAdded) {
                out.write(
                    "<html>" +
                    "<head>"+
                    "<title>Food Item Added</title>"+
                    "<link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css\" integrity=\"sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2\" crossorigin=\"anonymous\">" +
                    "<style>" +
                    "body{" +
                        "font-family: Montserrat, sans-serif;" +
                        "background-image: linear-gradient(to bottom right, #2AF598, #08AEEA);" +
                        "margin: 0;"+ 
                        "display: flex;"+
                        "align-items: center;"+
                        "justify-content: center;"+
                        "height: 100vh;"+
                    "}" +
                    ".container {" +
                        "text-align: center;" +
                        "background-color: rgba(255,255,255,0.8);" +
                        "width: 80%;" +
                        "padding: 40px;" +
                        "border-radius:30px;"+
                    "}" +
                    "table {" +
                        "width: 100%;" +
                        "text-align: center;" +
                        "margin-bottom: 40px"+
                    "}" +
                    "table td {" +  
                        "margin-top: 20px;" +
                        "padding-top: 20px;" +
                    "}" +
                    "table th {" +  
                        "padding-bottom: 14px;" +
                    "}" +
                    "table .trow {" +  
                        "border-bottom: 2px solid #08AEEA;" +
                        "margin-bottom: 100px;" +
                    "}" +
                    ".innerCon {" +  
                        "margin-top: 70px;" +
                    "}" +
                    "</style>"+
                    "</head>" +
                    "<body>" +
                    "<div class='container'>"+
                    "<h2>Food Item Successfully Added</h2>" +
                    "<div class='innerCon'>"+
                    "<table>" +
                    "<thead>" +
                    "<tr class='trow'>" +
                    "<th>Food Item</th>" +
                    "<th>Product ID</th>" +
                    "<th>Product SKU</th>" +
                    "<th>Product Description</th>" +
                    "<th>Product Category</th>" +
                    "<th>Product Price</th>" +
                    "<th>Expiry Date</th>" +
                    "</tr>" +
                    "</thead>" +
                    "<tbody>" +
                    "<tr>" +
                    "<td>" + foodItem.getId() + "</td>" +
                    "<td>" + foodProduct.getID() + "</td>" +
                    "<td>" + foodProduct.getSKU() + "</td>" +
                    "<td>" + foodProduct.getDescription() + "</td>" +
                    "<td>" + foodProduct.getCategory() + "</td>" +
                    "<td>" + foodProduct.getPrice() + "</td>" +
                    "<td>" + foodItem.getExpiryDate() + "</td>" +
                    "</tr>" +
                    "</tbody>" +
                    "</table>" +
                    "</div>"+
                    "<a href=\"/foodItemH\">Back to Food Item List</a>" +
                    "</div>"+
                    "</body>" +
                    "</html>"
                );
            } else {
                throw new SQLException("Failed to add food item");
            }

        } catch (SQLException | IllegalArgumentException e) {
            e.printStackTrace();
            System.out.println("Error: Failed to add food item");
            out.write(
                "<html>" +
                "<head><title>Error</title>" +
                "<link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css\" integrity=\"sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2\" crossorigin=\"anonymous\">" +
                "<style>" +
                "body{" +
                "font-family: Montserrat, sans-serif;" +
                "background-image: linear-gradient(to bottom right, #2AF598, #08AEEA);" +
                "margin: 0;" +
                "display: flex;" +
                "align-items: center;" +
                "justify-content: center;" +
                "height: 100vh;" +
                "}" +
                ".container {" +
                "text-align: center;" +
                "background-color: rgba(255,255,255,0.8);" +
                "width: 60%;" +
                "padding: 40px;" +
                "border-radius:30px;" +
                "}" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<div class='container'>"+
                "<h1>Error adding food item</h1>" +
                "<p>Please check the entered values and try again</p>" +
                "<p><a href=\"/foodItemH\">Back to Food Item List</a></p>" +
                "</div>" +
                "</body>" +
                "</html>"
            );
        } finally {
            out.close();
        }
        
    }
}
