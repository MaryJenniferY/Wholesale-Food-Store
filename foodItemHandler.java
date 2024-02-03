import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * The foodItemHandler class handles HTTP requests for food items.
 * It uses a Data Access Object (DAO) to interact with the database.
 * It sorts food items based on the price and sends the result as an HTML response.
 */
public class foodItemHandler implements HttpHandler {

    /**
    * The foodItemDAO instance used to interact with the database.
    */
    private foodItemDAO foodItemDAO;

    /**
    * Constructor for the foodItemHandler class.
    * Initializes the foodItemDAO instance.
    */
    public foodItemHandler() {
        this.foodItemDAO = new foodItemDAO();
    }

    /**
    * Handles the HTTP request and response.
    * Retrieves all food items from the database, sorts them based on the price,
    * and sends the result as an HTML response.
    *
    * @param he The HttpExchange object representing the HTTP request and response.
    * @throws IOException If an input or output error occurs.
    */
    @Override
    public void handle(HttpExchange he) throws IOException {
        System.out.println("Food Item Handler Called");

        he.sendResponseHeaders(200, 0);
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(he.getResponseBody()));

        try {
            ArrayList<FoodItem> foodItems = foodItemDAO.findAllFoodItems();

            // Check for sorting parameter in the URL
            String sortParam = he.getRequestURI().getQuery();
            if (sortParam != null && sortParam.equals("sort=price")) {
                // Sorting foodItems based on price (lowest to highest)
                System.out.println("Food Items sorted based on price (lowest to highest)");
                foodItems.sort(Comparator.comparingInt(item -> item.getFoodProduct().getPrice()));
            }

            out.write(
                "<html>" +
                "<head> <title>Food Item List</title> " +
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
                        
                    "}"+
                    "body{"+
                        "padding: 2% 0;"+
                        "font-family: Montserrat, sans-serif;" +
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
                "<body>" +
                "<div class='outterCon mx-auto'>"+
                "<div class=\"container\">" +
                "<h1 class=\"mb-4\">Food Item List</h1>" +
                "<div class\"row justify-content-around\">"+
                    "<div class='inner-div col-md-3 mt-4 text-left'>"+
                        "<a href='/foodItemAdd'>Add New Food Item</a>" +
                    "</div>"+
                    "<div class='inner-div col-md-3 mt-4'>"+
                        "<a class='pr-5' href='/ExpiredFP'>View Expired Food Items</a>" +
                    "</div>"+
                    "<div class='inner-div col-md-3 mt-4'>"+
                        "<a class='pl-5' href='/foodItemHandler?sort=price'>Sort by Price</a>"+
                    "</div>"+
                    "<div class='inner-div col-md-3 mt-4 text-right'>"+
                        "<a href='/shoppingBasket'>Place an Order</a>"+
                    "</div>"+
                "</div>"+
                "<table class=\"table mt-3\">" +
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

            for (FoodItem foodItem : foodItems) {
                FoodProduct product = foodItem.getFoodProduct();

                out.write(
                    "<tr>" +
                    "<td>" + foodItem.getId() + "</td>" +
                    "<td>" + product.getID() + "</td>" +
                    "<td>" + product.getSKU() + "</td>" +
                    "<td>" + product.getDescription() + "</td>" +
                    "<td>" + product.getCategory() + "</td>" +
                    "<td>" + product.getPrice() + "</td>" +
                    "<td>" + foodItem.getExpiryDate() + "</td>" +
                    "<td><a href='/foodItemDelete?id=" + foodItem.getId() + "'>Delete</a></td>" +  
                    "</tr>"
                );
            }

            out.write(
                "</tbody>" +
                "</table>" +
                "<a href='/adminWelcome'>Back to Home</a>" +
                "<p><a href='/'>Logout</a></p>" +
                "</div>" +
                "</div>" +
                "</body>" +
                "</html>");

        } catch (Exception e) {
            e.printStackTrace();
            out.write("<p>Error fetching food items</p>");
        } finally {
            out.close();
        }
    }
}
