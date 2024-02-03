import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.SQLException;
import java.util.Map;

/**
 * This class handles HTTP requests for updating food products.
 * It parses the form data, updates the product in the database, and sends a response.
 * If there's an error during this process, it prints the stack trace and sends an error message.
 */
public class FPUProcessHandler implements HttpHandler {

    /**
    * Handles the HTTP exchange by parsing form data, updating the food product in the database,
    * and sending a response to the client.
    *
    * @param he the HTTP exchange object containing the HTTP request
    * @throws IOException if an I/O error occurs
    */
    @Override
    public void handle(HttpExchange he) throws IOException {
        System.out.println("Food Product Update Process Handler Called");

        he.sendResponseHeaders(200, 0);
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(he.getResponseBody()));

        try {
            // Parse form data
            Map<String, String> formData = Util.parseFormData(he);

            System.out.println("This is the data "+formData);

            // Get details from the user
            int productID = Integer.parseInt(formData.get("productID"));
            String SKU = formData.get("SKU");
            String description = formData.get("description");
            String category = formData.get("category");
            
            int price;
            try {
                price = Integer.parseInt(formData.get("price"));
                if (price < 0) {
                    throw new NumberFormatException("Price cannot be negative");
                }
            } catch (NumberFormatException e) {

                e.printStackTrace();

                BufferedWriter errorOut = new BufferedWriter(new OutputStreamWriter(he.getResponseBody()));
                errorOut.write(
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
                "<h2>Error updating food product</h2>" +
                "<p>Please check the entered values and try again</p>"+
                "<p><a href=\"/foodProductH\">Back to Food Products</a></p>" +
                "</div>"+
                "</body>" +
                "</html>"
                );
                errorOut.close();

                throw e;

            }


            // Update the food product in the database
            FoodProductDAO FPD = new FoodProductDAO();
            FoodProduct updatedProduct = new FoodProduct(productID, SKU, description, category, price);
            boolean foodProductUpdated = FPD.updateProduct(updatedProduct);

            if(foodProductUpdated){
                out.write(
                "<html>" +
                "<head>"+
                    "<title>Food Product Updated</title>"+
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
                "<h2>Food Product Successfully Updated</h2>" +
                "<div class='innerCon'>"+
                "<table>" +
                "<thead>" +
                "<tr class='trow'>" +
                "<th>Product ID</th>" +
                "<th>SKU</th>" +
                "<th>Description</th>" +
                "<th>Category</th>" +
                "<th>Price</th>" +
                "</tr>" +
                "</thead>" +
                "<tbody>" +
                "<tr>" +
                "<td>" + updatedProduct.getID() + "</td>" +
                "<td>" + updatedProduct.getSKU() + "</td>" +
                "<td>" + updatedProduct.getDescription() + "</td>" +
                "<td>" + updatedProduct.getCategory() + "</td>" +
                "<td>" + updatedProduct.getPrice() + "</td>" +
                "</tr>" +
                "</tbody>" +
                "</table>" +
                "</div>"+
                "<a href=\"/foodProductH\">Back to Food Products</a>" +
                "</div>"+
                "</body>" +
                "</html>"
            );
            }else {
                throw new SQLException("Failed to update Food Product");
            }
            

        } catch (SQLException e) {
            e.printStackTrace(); 
           
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
                "<h2>Error updating food product</h2>" +
                "<p>Please check the entered values and try again</p>"+
                "<p><a href=\"/foodProductH\">Back to Food Products</a></p>" +
                "</div>"+
                "</body>" +
                "</html>"
            );
        }

        out.close();
    }
}