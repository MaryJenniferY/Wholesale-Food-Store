import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.ArrayList;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

/**
 * This class handles HTTP requests for a shopping basket application.
 * It manages the addition and removal of food items from the shopping basket.
 */
public class ShoppingBasketHandler implements HttpHandler {

    /**
    * List of all food items.
    */
    private ArrayList<FoodItem> foodItemList = new ArrayList<>();
    
    /**
    * List of selected food items for purchase.
    */
    private ArrayList<FoodItem> shoppingBasket = new ArrayList<>();
    
    /**
    * Total cost of the items in the shopping basket.
    */
    private int totalAmount = 0;

    /**
    * Handles incoming HTTP requests.
    * @param he HttpExchange object containing details of the request.
    * @throws IOException if an input or output error occurs.
    */
    public void handle(HttpExchange he) throws IOException {
        String method = he.getRequestMethod();
        if (method.equals("GET")) {
            handleGetRequest(he);
        } else if (method.equals("POST")) {
            handlePostRequest(he);
        }
    }

    /**
    * Handles GET requests by fetching all food items and displaying them.
    * @param he HttpExchange object containing details of the request.
    * @throws IOException if an input or output error occurs.
    */
    private void handleGetRequest(HttpExchange he) throws IOException {
        he.sendResponseHeaders(200, 0);
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(he.getResponseBody()));

        System.out.println("Shopping Basket Handler Called");

        foodItemDAO foodItemtDAO = new foodItemDAO();
        try {
            // Fetch all food items
            foodItemList = foodItemtDAO.findAllFoodItems();

            // Display food item list
            displayFoodItemList(out);

            // Display shopping basket
            displayShoppingBasket(out);

        } catch (SQLException e) {
            e.printStackTrace();
            out.write("<tr><td colspan=\"4\">Error Fetching Food Items</td></tr>");
        }

        out.close();
    }

    /**
    * Displays the list of all food items.
    * @param out BufferedWriter object used for writing the output.
    * @throws IOException if an input or output error occurs.
    */
    private void displayFoodItemList(BufferedWriter out) throws IOException {
        // Display food item list 
        out.write(
        "<html>" +
        "<head> "+
        "<title>Shopping Basket</title> " +
        "<link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css\" crossorigin=\"anonymous\">" +
        "<style>" +
        "body{"+
            "font-family: Montserrat, sans-serif;" +
            "background-image: linear-gradient(to bottom right, #2AF598, #08AEEA);"+
            "display: flex;" +
            "justify-content: center;" +
            "align-items: center;" +
            "padding: 40px 0 40px;"+
            "text-align: center;"+
        "}" +
        ".container{"+
        "min-width:95%;"+
        "justify-content: center;"+
        "align-items: center;"+
        "flex-direction: column;" +
        "}"+
        ".outterCon{"+
        "display: flex;" +
        "justify-content: space-between;" +
        "}"+
        ".innerCon{"+
        "min-width:49%;"+
        "background-color: rgba(255,255,255,0.8);"+
        "padding: 30px 15px;" +
        "}"+
        ".subBut{"+
            "background-image: linear-gradient(to bottom right, #2AF598, #08AEEA);"+
        "}"+
        "table th{"+
            "text-align: center;"+
        "}"+
        "</style>" +
        "</head>" +
        "<body>" +
        "<div class=\"container\">" +
        "<div class=\"row outterCon\">" +
        "<div class=\"col-md-5 innerCon\" >" +
        "<h2 class=\"mb-4\">Food Item List</h2>"+
        "<table class=\"table\">"+
        "<thead>"+
        "<tr>"+
        "<th>Food Item ID</th>"+
        "<th>Description</th>"+
        "<th>Price</th>"+
        "<th>Action</th>"+
        "</tr>"+
        "</thead>"+
        "<tbody>"
        );

        for (FoodItem item : foodItemList) {
            out.write(
                "<tr>" +
                    "<td>" + item.getId() + "</td>" +
                    "<td>" + item.getFoodProduct().getDescription() + "</td>" +
                    "<td>" + item.getFoodProduct().getPrice() + "</td>" +
                    "<td>" +
                        "<form method='post' action='/shoppingBasket'  >" +
                            "<input type='hidden' name='foodItemID' value='" + item.getId() + "'>" +
                            "<input type='hidden' name='description' value='" + item.getFoodProduct().getDescription() + "'>" +
                            "<input type='hidden' name='price' value='" + item.getFoodProduct().getPrice() + "'>" +
                            "<input type='hidden' name='action' value='addToBasket'>" +
                            "<input type='submit' value='Add to Basket' class='subBut'>" +
                        "</form>" +
                    "</td>" +
                "</tr>"
            );
        }

        out.write(
            "</tbody>"+
            "</table>"+
            "</div>"
        );
    }

    /**
    * Displays the current items in the shopping basket.
    * @param out BufferedWriter object used for writing the output.
    * @throws IOException if an input or output error occurs.
    */
    private void displayShoppingBasket(BufferedWriter out) throws IOException {
        // Display shopping basket 
        out.write(
            "<div class=\"col-md-5 innerCon\">" +
            "<h2 class=\"mb-4\">Shopping Basket</h2>"+
        "<table class=\"table\">"+
        "<thead>"+
        "<tr>"+
        "<th>Food Item ID</th>"+
        "<th>Description</th>"+
        "<th>Price</th>"+
        "<th>Action</th>"+
        "</tr>"+
        "</thead>"+
        "<tbody>"
        );

        for (FoodItem basketItem : shoppingBasket) {
            out.write(
                "<tr>" +
                    "<td>" + basketItem.getId() + "</td>" +
                    "<td>" + basketItem.getFoodProduct().getDescription() + "</td>" +
                    "<td>" + basketItem.getFoodProduct().getPrice() + "</td>" +
                    "<td>" +
                        "<form method='post' action='/shoppingBasket'  >" +
                            "<input type='hidden' name='foodItemID' value='" + basketItem.getId() + "'>" +
                            "<input type='hidden' name='description' value='" + basketItem.getFoodProduct().getDescription() + "'>" +
                            "<input type='hidden' name='price' value='" + basketItem.getFoodProduct().getPrice() + "'>" +
                            "<input type='hidden' name='action' value='removeFromBasket'>" +
                            "<input type='submit' value='Remove from Basket' class='subBut'>" +
                        "</form>" +
                    "</td>" +
                "</tr>"
            );
        }

        out.write(
            "<tr>" +
                "<td colspan='2'><strong>Total Amount:</strong></td>" +
                "<td><strong>" + totalAmount + "</strong></td>" +
                "<td style='text-align: center;'><input type='submit' value='Submit Order' class='subBut'></td>" +
            "</tr>" +
            "</tbody>" +
            "</table>" +
            "</div>" + 
                        "</div>" + 
                        "<div class='mt-3'>" +
                        "<a href='/foodItemH'>Back To Food Items</a>" +
                        "<p><a href='/adminWelcome'>Back to Home</a></p>" +
                        "<a href='/'>Logout</a>" +
                        "</div>" +
                    "</div>" + 
                "</body>" +
                "</html>"
        );
    }

    /**
    * Handles POST requests by adding or removing items from the shopping basket.
    * @param he HttpExchange object containing details of the request.
    * @throws IOException if an input or output error occurs.
    */
    private void handlePostRequest(HttpExchange he) throws IOException {
        if ("/shoppingBasket".equals(he.getRequestURI().getPath())) {
            handleAddRemoveFromBasket(he);
        }
    }

    /**
    * Adds or removes items from the shopping basket based on the action specified in the request.
    * @param he HttpExchange object containing details of the request.
    * @throws IOException if an input or output error occurs.
    */
    private void handleAddRemoveFromBasket(HttpExchange he) throws IOException {
        InputStreamReader isr = new InputStreamReader(he.getRequestBody(), StandardCharsets.UTF_8);
        BufferedReader br = new BufferedReader(isr);
        String formData = br.readLine();
    
        String[] params = formData.split("&");
        int foodItemID = Integer.parseInt(params[0].split("=")[1]);
        String description = URLDecoder.decode(params[1].split("=")[1], StandardCharsets.UTF_8.name());
        int price = Integer.parseInt(params[2].split("=")[1]);
        String action = params[3].split("=")[1];
    
        FoodProduct product = new FoodProduct(0, null, description, null, price);
        FoodItem item = new FoodItem(foodItemID, product, null);
    
        if ("addToBasket".equals(action)) {
            
            shoppingBasket.add(item);
            totalAmount += price;
        } else if ("removeFromBasket".equals(action)) {
            // Remove from shopping basket and add back to food item list
            shoppingBasket.removeIf(i -> i.getId() == foodItemID);
            foodItemList.add(item);
            totalAmount -= price;
        }
    
        // Redirect back to the shopping basket page
        String redirectUrl = "/shoppingBasket";
        he.getResponseHeaders().set("Location", redirectUrl);
        he.sendResponseHeaders(302, -1);
    }
}
