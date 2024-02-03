import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * This class, BCAddHandler, implements the HttpHandler interface.
 * It handles HTTP requests to add a customer.
 * It sends a response with a form to enter customer details.
 */
public class BCAddHandler implements HttpHandler {

    /**
    * Overrides the handle method from the HttpHandler interface.
    * This method is invoked when a request is received.
    * It writes a response containing an HTML form for adding a customer.
    *
    * @param he an HttpExchange object containing the exchange between client and server
    * @throws IOException if an I/O error occurs during the processing of the request
    */
    @Override
    public void handle(HttpExchange he) throws IOException {
        
        System.out.println("Customer Add Handler Called");

        he.sendResponseHeaders(200, 0);

        // BufferedWriter for writing the HTML response
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(he.getResponseBody()));

        out.write(
            "<html>" +
            "<head>"+
            "<title>Add Customer</title>"+
            "<link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css\" integrity=\"sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2\" crossorigin=\"anonymous\">" +
            "<style>" +
            "body{" +
                "font-family: Montserrat, sans-serif;" +
                "background-image: linear-gradient(to bottom right, #2AF598, #08AEEA);" +
                "margin: 0;"+ 
                "display: flex;"+
                "align-items: center;"+
                "justify-content: center;"+
                "padding:40px;"+
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
            "<h2>Add Customer</h2>" +
            "<form action=\"/BCAProcess\" method=\"post\">" +
            "<div>"+
            "Enter Business Name: <br>" +
            "<input type=\"text\" name=\"businessName\"><br>" +
            "</div>"+
            "<div class='row'>"+
            "<div class='col-md-6'>"+
            "Enter Address Line 1: <br>" +
            "<input type=\"text\" name=\"addressLine1\"><br>" +
            "Enter Address Line 2: <br>" +
            "<input type=\"text\" name=\"addressLine2\"><br>" +
            "Enter Address Line 3: <br>" +
            "<input type=\"text\" name=\"addressLine3\"><br>" +
            "</div>"+
            "<div class='col-md-6'>"+
            "Enter Country: <br>" +
            "<input type=\"text\" name=\"country\"><br>" +
            "Enter Postcode: <br>" +
            "<input type=\"text\" name=\"postCode\"><br>" +
            "Enter Telephone Number: <br>" +
            "<input type=\"text\" name=\"teleNumber\"><br>" +
            "</div>"+
            "</div>"+
            "<input type=\"submit\" value=\"Submit\" class='mt-3 subBut'>" +
            "</form>" +
            "<p><a href=\"/customerH\">Back to Customers</a></p>" +
            "</body>" +
            "</html>"
        );

        out.close();
    }
}
