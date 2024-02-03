import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.SQLException;

/**
 * This class handles HTTP requests for deleting food items.
 * It extracts the food item ID from the request, fetches the existing details of the food item,
 * attempts to delete the food item, and sends an appropriate response.
 */
public class FIDeleteHandler implements HttpHandler {

    /**
    * Handles the HTTP exchange.
    * 
    * @param he the HttpExchange object containing the request and response
    * @throws IOException if an I/O error occurs
    */
    @Override
    public void handle(HttpExchange he) throws IOException {
        // Extract food item ID from the query parameter
        String query = he.getRequestURI().getQuery();
        String[] queryParams = query.split("=");
        int foodItemID = Integer.parseInt(queryParams[1]);

        // Fetch the existing details of the food item
        foodItemDAO foodItemDAO = new foodItemDAO();
        FoodItem deletedFoodItem = null;
        boolean deletionSuccess = false;

        try {
            System.out.println("Attempting to delete food item with ID: " + foodItemID);
            deletedFoodItem = foodItemDAO.findFoodItem(foodItemID);

            if (deletedFoodItem != null) {
                deletionSuccess = foodItemDAO.deleteFoodItem(foodItemID);

                if (deletionSuccess) {
                    System.out.println("Food item successfully deleted");
                } else {
                    System.out.println("Food item deletion failed");
                }
                
            } else {
                System.out.println("Food item not found for deletion");
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        }

        he.sendResponseHeaders(200, 0);
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(he.getResponseBody()));

        //if deletion was successful display details
        if (deletionSuccess) {
            
            out.write(
                "<html>" +
                "<head>"+
                    "<title>Food Item Deleted</title>"+
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
                "<h2>Food Item Successfully Deleted</h2>" +
                "<div class='innerCon'>"+
                "<table>" +
                "<thead>" +
                "<tr class='trow'>" +
                "<th>Food Item ID</th>" +
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
                "<td>" + deletedFoodItem.getId() + "</td>" +
                "<td>" + deletedFoodItem.getFoodProduct().getID() + "</td>" +
                "<td>" + deletedFoodItem.getFoodProduct().getSKU() + "</td>" +
                "<td>" + deletedFoodItem.getFoodProduct().getDescription() + "</td>" +
                "<td>" + deletedFoodItem.getFoodProduct().getCategory() + "</td>" +
                "<td>" + deletedFoodItem.getFoodProduct().getPrice() + "</td>" +
                "<td>" + deletedFoodItem.getExpiryDate() + "</td>" +
                "</tr>" +
                "</tbody>" +
                "</table>" +
                "</div>"+
                "<p><a href=\"/foodItemH\">Back to Food Items</a></p>" +
                "</div>"+
                "</body>" +
                "</html>"
            );
        } else {
            // Display an error message if deletion failed or item not found
            out.write(
                "<html>" +
                "<head><title>Error</title>"+
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
                        "width: 60%;" +
                        "padding: 40px;" +
                        "border-radius:30px;"+
                    "}" +
                    "</style>"+
                "</head>" +
                "<body>" +
                "<div class='container'>"+
                "<h2>Error deleting food item</h2>" +
                "<p>Food item not found or deletion failed</p>" +
                "<p><a href=\"/foodItemH\">Back to Food Items</a></p>" +
                "</div>"+
                "</body>" +
                "</html>"
            );
        }

        out.close();
    }
}
