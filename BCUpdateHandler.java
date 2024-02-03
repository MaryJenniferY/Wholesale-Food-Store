import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.SQLException;

/**
 * BCUpdateHandler is a class implementing HttpHandler to handle HTTP requests for updating business customers.
 * It extracts the customer ID from the request URI, fetches the existing customer details, and sends an HTML response
 * containing a form to update the customer details.
 */
public class BCUpdateHandler implements HttpHandler {

    /**
     * Handles the HTTP request to update business customer details.
     *
     * @param he the HTTP exchange containing the request and response
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void handle(HttpExchange he) throws IOException {
        // Extract customer ID from the query parameter
        String query = he.getRequestURI().getQuery();
        String[] queryParams = query.split("=");
        int customerID = Integer.parseInt(queryParams[1]);

        // Fetch the existing details of the customer
        CustomerDAO BCD = new CustomerDAO();
        Customer existingCustomer = null;
        try {
            existingCustomer = BCD.findCustomer(customerID);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("Customer Add Handler Called");
        he.sendResponseHeaders(200, 0);
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(he.getResponseBody()));

        out.write(
            "<html>" +
            "<head>"+
            "<title>Update Customer</title>"+
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
            "<h2>Edit Business Customer</h2>" +
            "<form action=\"/BCUProcess\" method=\"post\">" +
            "<input type=\"hidden\" name=\"customerID\" value=\"" + customerID + "\">" +
            "<div>"+
            "Enter New Business Name: <br>" +
            "<input type=\"text\" name=\"businessName\" value=\"" + existingCustomer.getBusinessName() + "\"><br>" +
            "</div>"+
            "<div class='row'>"+
            "<div class='col-md-6'>"+
            "Enter New Address Line 1: <br>" +
            "<input type=\"text\" name=\"addressLine1\" value=\"" + existingCustomer.getAddress().getAddressLine1() + "\"><br>" +
            "Enter New Address Line 2: <br>" +
            "<input type=\"text\" name=\"addressLine2\" value=\"" + existingCustomer.getAddress().getAddressLine2() + "\"><br>" +
            "Enter New Address Line 3: <br>" +
            "<input type=\"text\" name=\"addressLine3\" value=\"" + existingCustomer.getAddress().getAddressLine3() + "\"><br>" +
            "</div>"+
            "<div class='col-md-6'>"+
            "Enter New Country: <br>" +
            "<input type=\"text\" name=\"country\" value=\"" + existingCustomer.getAddress().getCountry() + "\"><br>" +
            "Enter New Postcode: <br>" +
            "<input type=\"text\" name=\"postCode\" value=\"" + existingCustomer.getAddress().getPostCode() + "\"><br>" +
            "Enter New Telephone Number: <br>" +
            "<input type=\"text\" name=\"teleNumber\" value=\"" + existingCustomer.getTeleNumber() + "\"><br>" +
            "</div>"+
            "</div>"+
            "<input type=\"submit\" value=\"Update\" class='mt-3 subBut'>" +
            "</form>" +
            "<p><a href=\"/customerH\">Back to Business Customers</a></p>" +
            "</div>"+
            "</body>" +
            "</html>"
        );

        out.close();
    }
}