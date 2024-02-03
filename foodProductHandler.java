import java.io.*;
import java.net.URI;
import java.sql.SQLException;
import java.util.*;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

/**
 * This class handles HTTP requests and responses for food products.
 * It uses the FoodProductDAO class to interact with the database.
 * The handler responds to GET requests with different filters for product details.
 * The filters can be applied based on product ID, category, or description.
 * If no filters are applied, it displays all food products.
 * It also handles any SQL exceptions that occur during data retrieval.
 */
public class foodProductHandler implements HttpHandler {

    /**
    * This method is called whenever a request is received.
    * It sends a response header indicating a successful request.
    * Then it writes the response body, which is an HTML page displaying food products.
    * The products displayed are determined by the filters in the request URI.
    * If a filter is not provided, all products are displayed.
    *
    * @param he The HttpExchange object representing the exchange of an HTTP request and response.
    * @throws IOException If an I/O error occurs.
    */
    public void handle(HttpExchange he) throws IOException {
        System.out.println("Food Product Handler Called");

        he.sendResponseHeaders(200, 0);

        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(he.getResponseBody()));

        Map<String, String> params = queryToMap(he.getRequestURI());
        String filterProductId = params.get("filterProductId");
        String filterCategory = params.get("filterCategory");
        String filterDescription = params.get("filterDescription");

        // FoodProductDAO instance
        FoodProductDAO FPH = new FoodProductDAO();


        try {
            if (filterProductId != null) {
                // Display details of a specific product based on the filtered product ID
                System.out.println("Displaying the food product with product ID: "+filterProductId);
                int productId = Integer.parseInt(filterProductId);
                FoodProduct product = FPH.findProduct(productId);
                ArrayList<FoodProduct> productList = new ArrayList<>();
                if (product != null) {
                    productList.add(product);
                }
                displayAllProducts(out, productList);
            } else if (filterCategory != null) {
                // Display details of products in the specified category
                System.out.println("List of food products with category: "+filterCategory);
                ArrayList<FoodProduct> productsInCategory = FPH.findProductsByCategory(filterCategory);
                displayAllProducts(out, productsInCategory);
            } else if (filterDescription != null) {
                // Display details of products with descriptions containing the specified word
                System.out.println("List of food products with description containing the word: "+filterDescription);
                ArrayList<FoodProduct> productsWithDescription = FPH.findProductsByDescription(filterDescription);
                displayAllProducts(out, productsWithDescription);
            } else {
                // Display the list of all food products
                System.out.println("Displaying All Food Products from stock Table");
                displayAllProducts(out, FPH.findAllProducts());
            }

        } catch (SQLException e) {
            System.out.println("Error fetching food products: " + e.getMessage());
            e.printStackTrace();
            out.write("<p>Error fetching food products</p>");
        } finally {
            out.close();
        }

    }
    
    /**
    * This method displays all given food products in an HTML table format.
    * The HTML page allows users to filter products by ID, category, or description.
    * It also provides links to update or delete each product.
    *
    * @param out The BufferedWriter to write the HTML code to.
    * @param products The list of food products to display.
    * @throws IOException If an I/O error occurs.
    */
    void displayAllProducts(BufferedWriter out, ArrayList<FoodProduct> products) throws IOException {

        out.write(
            "<html>" +
                "<head> <title>Food Product List</title> " +
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
                "<h1 class=\"mb-4\">Food Products</h1>" +
                "<div class\"row justify-content-around\">"+
                "<form action='/foodProductHandler' method='get' class='col-md-3 mt-4 text-left'>" +
                "<input type='text' name='filterProductId' placeholder='Filter by Product ID'  class='mr-1'/>" +
                "<input class='subBut' type='submit' value='Find' />" +
                "</form>" + 
                "<form action='/foodProductHandler' method='get' class='col-md-3 mt-4'>" +
                "<input type='text' name='filterDescription' placeholder='Filter by Description'  class='mr-1'/>" +
                "<input class='subBut' type='submit' value='Find' />" +
                "</form>" +
                "<form action='/foodProductHandler' method='get' class='col-md-3 mt-4'>" +
                "<input type='text' name='filterCategory' placeholder='Filter by Category' class='mr-1'/>" +
                "<input class='subBut' type='submit' value='Find' />" +
                "</form>" +
                "<div class = 'col-md-3 inner-div mt-4 text-right'>"+
                        "<a href='/FPAdd'>Add New Product</a>" +
                "</div>"+
                "</div>"+
                "<table class=\"table\">" +
                "<thead>" +
                "<tr>" +
                "<th>Product ID</th>" +
                "<th>SKU</th>" +
                "<th>Description</th>" +
                "<th>Category</th>" +
                "<th>Price</th>" +
                "<th>Edit</th>" +
                "<th>Delete</th>" +
                "</tr>" +
                "</thead>" +
                "<tbody>");

        for (FoodProduct product : products) {
            out.write(
                    "<tr>" +
                        "<td>" + product.getID() + "</td>" +
                        "<td>" + product.getSKU() + "</td>" +
                        "<td>" + product.getDescription() + "</td>" +
                        "<td>" + product.getCategory() + "</td>" +
                        "<td>" + product.getPrice() + "</td>" +
                        "<td><a href='/FPUpdate?id=" + product.getID() + "'>Edit</a></td>" +
                        "<td><a href='/FPDelete?id=" + product.getID() + "'>Delete</a></td>" +
                    "</tr>"
            );
        }

        out.write(
                "</tbody>" +
            "</table>" +
            "<a href='/adminWelcome'>Back to Home</a>" +
            "<p><a href='/'>Logout</a></p>" +
            "</div>" +
            "</body>" +
            "</html>");

    }

    /**
    * This method converts the query parameters of a URI into a map.
    * Each parameter is represented as a key-value pair in the map.
    *
    * @param uri The URI whose query parameters are to be converted.
    * @return A map of the query parameters.
    */
    Map<String, String> queryToMap(URI uri) {

        // Convert query parameters to a map
        Map<String, String> queryMap = new HashMap<>();
        String query = uri.getQuery();

        if (query != null) {
            String[] pairs = query.split("&");
            for (String pair : pairs) {
                String[] keyValue = pair.split("=");
                if (keyValue.length == 2) {
                    queryMap.put(keyValue[0], keyValue[1]);
                }
            }
        }

        return queryMap;
    }
}
