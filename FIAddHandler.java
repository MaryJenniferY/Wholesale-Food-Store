import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * FIAddHandler is a custom HttpHandler implementation for handling HTTP requests
 * related to adding food items.
 */
public class FIAddHandler implements HttpHandler {

    /**
    * Handles an HTTP request. Sends a response with HTML content for adding a new food item.
    *
    * @param he The HttpExchange object encapsulating the incoming request and the response.
    * @throws IOException If an I/O error occurs during handling the request.
    */
    @Override
    public void handle(HttpExchange he) throws IOException {
        he.sendResponseHeaders(200, 0);
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(he.getResponseBody()));
        System.out.println("Food Item Add Handler Called");

        out.write(
            "<html>" +
            "<head>"+
            "<title>Add Food Item</title>"+
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
            "<div class=\"container\">" +
            "<h2>Add New Food Item</h2>" +
            "<form action='/FIAProcess' method='post'>" +
            "<label for='productID'>Enter Food Product ID:</label>" +
            "<br>" +
            "<input type='text' id='productID' name='productID' required><br>" +
            "<label for='expiryDate'>Enter Expiry Date:</label>" +
            "<br>" +
            "<input type='date' id='expiryDate' name='expiryDate' placeholder='YYYY-MM-DD' required><br>" +
            "<input type='submit' value='Submit' class='mt-3 subBut'>" +
            "</form>" +
            "<br>" +
            "<a href='/foodItemH'>Back to Food Item List</a>" +
            "</div>" +
            "</body>" +
            "</html>");

        out.close();
    }
}
