import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.SQLException;

/**
 * This class handles HTTP requests for deleting food products.
 * It extracts the product ID from the request, attempts to delete the product, and sends an appropriate response.
 */
public class FPDeleteHandler implements HttpHandler {

    /**
    * Handles an HTTP exchange.
    * Extracts the product ID from the request URI, attempts to delete the product, and sends an appropriate response.
    *
    * @param he the HTTP exchange
    * @throws IOException if an input or output error occurs
    */
    @Override
    public void handle(HttpExchange he) throws IOException {
        System.out.println("Food Product Delete Handler Called");
        // Extract product ID from the query parameter
        String query = he.getRequestURI().getQuery();
        String[] queryParams = query.split("=");
        int productID = Integer.parseInt(queryParams[1]);

        // Fetch the existing details of the food product
        FoodProductDAO FPD = new FoodProductDAO();
        FoodProduct deletedProduct = null;
        boolean deletionSuccess = false;

        try {
            System.out.println("Attempting to delete food item with ID: " + productID);
            deletedProduct = FPD.findProduct(productID);
            
            if (deletedProduct != null) {
                deletionSuccess = FPD.deleteProduct(productID);

                if (deletionSuccess) {
                    System.out.println("Food Product Successfully Deleted");
                } else {
                    System.out.println("Food Product deletion failed");
                }
                
            } else {
                System.out.println("Food Product not found for deletion");
            }
            
        } catch (SQLException e) {
            e.printStackTrace(); 
            System.out.println("Error deleting food product");
        }

        he.sendResponseHeaders(200, 0);
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(he.getResponseBody()));

        //if deletion was success display details
        if (deletionSuccess) {
            out.write(
                "<html>" +
                "<head>"+
                    "<title>Food Product Deleted</title>"+
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
                "<h2>Food Product Successfully Deleted</h2>" +
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
                "<td>" + deletedProduct.getID() + "</td>" +
                "<td>" + deletedProduct.getSKU() + "</td>" +
                "<td>" + deletedProduct.getDescription() + "</td>" +
                "<td>" + deletedProduct.getCategory() + "</td>" +
                "<td>" + deletedProduct.getPrice() + "</td>" +
                "</tr>" +
                "</tbody>" +
                "</table>" +
                "</div>"+
                "<p><a href=\"/foodProductH\">Back to Food Products</a></p>" +
                "</div>"+
                "</body>" +
                "</html>"
            );
        } else {
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
                "<h2>Error deleting food product</h2>" +
                "<p>Food Product with the given ID doesn't exist</p>"+
                "<p><a href=\"/foodProductH\">Back to Food Products</a></p>" +
                "</div>"+
                "</body>" +
                "</html>"
            );
        }

        out.close();
    }
}