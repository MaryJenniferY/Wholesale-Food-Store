import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.sql.SQLException;

/**
 * ExpiredFPHandler is an HTTP handler for managing expired food items.
 * It uses a foodItemDAO object to interact with the database and retrieve the list of expired food items.
 * The handler responds with an HTML page displaying the details of these items.
 */
public class ExpiredFPHandler implements HttpHandler {

    /**
    * The DAO object for interacting with the food item data in the database.
    */
    private foodItemDAO foodItemDAO;

    /**
    * Constructor for ExpiredFPHandler. Initializes the foodItemDAO object.
    */
    public ExpiredFPHandler() {
        this.foodItemDAO = new foodItemDAO();
    }

    /**
    * Overridden method from HttpHandler. Handles the HTTP exchange, retrieves the list of expired food items,
    * and sends an HTML response containing the details of these items.
    * 
    * @param he The HttpExchange object representing the current HTTP request.
    * @throws IOException If an I/O error occurs during the handling of the HTTP exchange.
    */
    @Override
    public void handle(HttpExchange he) throws IOException {
        he.sendResponseHeaders(200, 0);
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(he.getResponseBody()));
        System.out.println("Expired Food Items Handler Called");

        try {
            ArrayList<FoodItem> expiredFoodItems = foodItemDAO.findExpiredFoodItems();

            out.write(
                "<html>" +
                "<head> <title>Expired Food Item List</title> " +
                "<link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css\" integrity=\"sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2\" crossorigin=\"anonymous\">" +
                "<style>"+
                    ".container {"+
                        "text-align: center;"+
                        "background-color: white;"+
                        "min-width: 100%;"+
                        "min-height:100%;"+
                        "padding:40px;"+
                    "}"+
                    ".outterCon {"+
                        "text-align: center;"+
                        "background-image: linear-gradient(to bottom right, #2AF598, #08AEEA);"+
                        "width:96%;"+
                        "min-height:98%;"+
                        "display: flex;"+
                        "justify-content: center;"+
                        "align-items: center;"+
                        "padding: 2% 2%;"+
                        "font-family: Montserrat, sans-serif;" +
                        "font-weight:100;"+
                    "}"+
                    "body{"+
                        "padding: 2% 0;"+
                    "}"+

                    ".inner-div {"+
                        "display: flex;"+
                        "justify-content: center;"+
                        "align-items: center;"+
                    "}"+

                    "form, .inner-div{"+
                       " display: inline-block;"+
                    "}"+

                    ".subBut{"+
                       "background-image: linear-gradient(to bottom right, #2AF598, #08AEEA);"+
                    "}"+

                "</style>"+
                "</head>" +
                "<link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css\" integrity=\"sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2\" crossorigin=\"anonymous\">" +
                "</head>" +
                "<body>" +
                "<div class='outterCon mx-auto'>"+
                "<div class=\"container\">" +
                "<h1 class=\"mb-4\">Expired Food Items</h1>" +
                "<table class=\"table\">" +
                "<thead>" +
                "<tr>" +
                "<th>Food Item ID</th>" +
                "<th>Product ID</th>" +
                "<th>Product SKU</th>" +
                "<th>Product Description</th>" +
                "<th>Product Category</th>" +
                "<th>Product Price</th>" +
                "<th>Expiry Date</th>" +
                "<th>Delete</th>" +  
                "</tr>" +
                "</thead>" +
                "<tbody>");

            for (FoodItem expiredFoodItem : expiredFoodItems) {
                FoodProduct product = expiredFoodItem.getFoodProduct();

                out.write(
                    "<tr>" +
                    "<td>" + expiredFoodItem.getId() + "</td>" +
                    "<td>" + product.getID() + "</td>" +
                    "<td>" + product.getSKU() + "</td>" +
                    "<td>" + product.getDescription() + "</td>" +
                    "<td>" + product.getCategory() + "</td>" +
                    "<td>" + product.getPrice() + "</td>" +
                    "<td>" + expiredFoodItem.getExpiryDate() + "</td>" +
                    "<td><a href='/foodItemDelete?id=" + expiredFoodItem.getId() + "'>Delete</a></td>" +
                    "</tr>"
                );
            }

            out.write(
                "</tbody>" +
                "</table>" +
                "<a href='/foodItemH'>Back to Food Items</a>" +
                "</div>" +
                "</body>" +
                "</html>");

        } catch (SQLException e) {
            e.printStackTrace();
            out.write("<p>Error fetching expired food items</p>");
        } finally {
            out.close();
        }
    }
}
