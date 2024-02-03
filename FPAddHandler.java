import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * This class handles HTTP requests for adding food products.
 * It sends a response containing an HTML form for entering details of a food product.
 */
public class FPAddHandler implements HttpHandler {

    /**
    * Handle an HTTP request.
    * Send a response containing an HTML form for entering details of a food product.
    *
    * @param he The exchange object containing the HTTP request/response pair.
    * @throws IOException If an input or output error occurs.
    */
    @Override
    public void handle(HttpExchange he) throws IOException {

        he.sendResponseHeaders(200, 0);
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(he.getResponseBody()));
        System.out.println("Food Product Add Handler Called");

        out.write(
            "<html>" +
            "<head>"+
            "<title>Add Food Product</title>"+
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
            "<h2>Add Food Product</h2>" +
            "<form action=\"/FPAProcess\" method=\"post\">" +
            "Enter SKU: <br> " +
            "<input type=\"text\" name=\"SKU\"><br>" +
            "Enter Description:<br>"+
            "<input type=\"text\" name=\"description\"><br>" +
            "Enter Category: <br>"+
            "<input type=\"text\" name=\"category\"><br>" +
            "Enter Price: <br>"+
            "<input type=\"text\" name=\"price\"><br>" +
            "<input type=\"submit\" value=\"Submit\" class='mt-3 subBut'>" +
            "</form>" +
            "<p><a href=\"/foodProductH\">Back to Food Products</a></p>" +
            "</div>"+
            "</body>" +
            "</html>"
        );

        out.close();
    }
}