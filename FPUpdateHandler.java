import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.SQLException;

/**
 * This class implements the HttpHandler interface to handle HTTP requests
 * for updating food product details. It retrieves existing product details, generates an HTML form for updating,
 * and sends the response with the form to the client.
 */
public class FPUpdateHandler implements HttpHandler {

    /**
    * This method handles the HTTP exchange request and sends the response headers.
    * It fetches the existing details of the food product based on the product ID extracted from the query parameters.
    * Then, it prepares an HTML page to update the food product details and writes it to the response body.
    * 
    * @param he the HttpExchange object representing the HTTP request and response.
    * @throws IOException if an input or output exception occurs.
    */
    @Override
    public void handle(HttpExchange he) throws IOException {
        // Extract product ID from the query parameter
        String query = he.getRequestURI().getQuery();
        String[] queryParams = query.split("=");
        int productID = Integer.parseInt(queryParams[1]);

        // Fetch the existing details of the food product
        FoodProductDAO FPD = new FoodProductDAO();
        FoodProduct existingProduct = null;
        try {
            existingProduct = FPD.findProduct(productID);
        } catch (SQLException e) {
            e.printStackTrace(); 
        }

        he.sendResponseHeaders(200, 0);
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(he.getResponseBody()));
        System.out.println("Food Product Update Handler Called");

        out.write(
            "<html>" +
            "<head>"+
            "<title>Update Food Product</title>"+
            "<link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css\" integrity=\"sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2\" crossorigin=\"anonymous\">" +
            "<style>" +
            "body{" +
                "font-family: Montserrat, sans-serif;" +
                "background-image: linear-gradient(to bottom right, #2AF598, #08AEEA);" +
                "margin: 0;"+ 
               " display: flex;"+
                "align-items: center;"+
                "justify-content: center;"+
                "height: 100vh;"+
            "}" +
            ".container {" +
                "text-align: center;" +
                "background-color: rgba(255,255,255,0.7);" +
                "width: 40%;" +
                "min-height: 70%;" +
                "padding: 40px;" +
                "border-radius:30px;"+
            "}" +
            "form{"+
                "margin-top: 30px;"+
            "}"+
            "input{"+
                "margin-bottom: 10px;"+
            "}"+
            ".subBut{"+
                "background-image: linear-gradient(to bottom right, #2AF598, #08AEEA);"+
            "}"+
            "</style>" +
            "</head>" +
            "<body>" +
            "<div class='container'>"+
            "<h2>Edit Food Product</h2>" +
            "<form action=\"/FPUProcess\" method=\"post\">" +
            "<input type=\"hidden\" name=\"productID\" value=\"" + productID + "\">" +
            "Enter New SKU: <br>" + 
            "<input type=\"text\" name=\"SKU\" value=\"" + existingProduct.getSKU() + "\"><br>" +
            "Enter New Description: <br>" + 
            "<input type=\"text\" name=\"description\" value=\"" + existingProduct.getDescription() + "\"><br>" +
            "Enter New Category: <br>" + 
            "<input type=\"text\" name=\"category\" value=\"" + existingProduct.getCategory() + "\"><br>" +
            "Enter New Price: <br>" + 
            "<input type=\"text\" name=\"price\" value=\"" + existingProduct.getPrice() + "\"><br>" +
            "<input type=\"submit\" value=\"Update\" class='mt-3 subBut'>" +
            "</form>" +
            "<p><a href=\"/foodProductH\">Back to Food Products</a></p>" +
            "</div>"+
            "</body>" +
            "</html>"
        );

        out.close();
    }
}