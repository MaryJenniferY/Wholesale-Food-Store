import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

/**
 * This class handles HTTP requests and responses for customer data.
 * It implements the HttpHandler interface from the com.sun.net.httpserver package.
 */
public class customerHandler implements HttpHandler {

    /**
    * Handles incoming HTTP requests. If the request method is GET, it calls handleGetRequest.
    * If the request method is POST, it also calls handleGetRequest.
    * 
    * @param he the HttpExchange containing the details of the HTTP request
    * @throws IOException if an input or output exception occurs
    */
    public void handle(HttpExchange he) throws IOException {
        String method = he.getRequestMethod();
        if (method.equals("GET")) {
            handleGetRequest(he);
        } else if (method.equals("POST")) {
            handleGetRequest(he);
        }
    }

    /**
    * Handles GET requests by sending a response containing a list of all customers.
    * It uses the CustomerDAO class to retrieve the list of customers from the database.
    * The response is formatted as an HTML table.
    * 
    * @param he the HttpExchange containing the details of the HTTP request
    * @throws IOException if an input or output exception occurs
    */
    private void handleGetRequest(HttpExchange he) throws IOException {
        he.sendResponseHeaders(200, 0);
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(he.getResponseBody()));

        System.out.println("Customer Handler Called");

        CustomerDAO BCD = new CustomerDAO();
        try {
            ArrayList<Customer> customers = BCD.findAllCustomers();

            out.write(
                    "<html>" +
                    "<head> <title>Customer List</title> " +
                "<link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css\" integrity=\"sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2\" crossorigin=\"anonymous\">" +
                "<style>"+
                    ".container {"+
                        "text-align: center;"+
                        "background-color: white;"+
                        "min-width: 100%;"+
                        "min-height:100%;"+
                        "padding:40px;"+
                        "font-family: Montserrat, sans-serif;" +
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
                    "<h1 class='mb-5' >Customer List</h1>" +
                    "<a href='/BCAdd' >Add New Customer</a>" +
                    "<table class=\"table mt-3\">" +
                    "<thead>" +
                    "<tr>" +
                    "<th>Customer ID</th>" +
                    "<th>Business Name</th>" +
                    "<th>Address Line 1</th>" +
                    "<th>Address Line 2</th>" +
                    "<th>Address Line 3</th>" +
                    "<th>Country</th>" +
                    "<th>Postcode</th>" +
                    "<th>Telephone Number</th>" +
                    "<th>Edit</th>" +
                    "<th>Delete</th>" +
                    "</tr>" +
                    "</thead>" +
                    "<tbody>");

            for (Customer customer : customers) {
                out.write(
                    "<tr>" +
                    "<td>" + customer.getCustomerID() + "</td>" +
                    "<td>" + customer.getBusinessName() + "</td>" +
                    "<td>" + customer.getAddress().getAddressLine1() + "</td>" +
                    "<td>" + customer.getAddress().getAddressLine2() + "</td>" +
                    "<td>" + customer.getAddress().getAddressLine3() + "</td>" +
                    "<td>" + customer.getAddress().getCountry() + "</td>" +
                    "<td>" + customer.getAddress().getPostCode() + "</td>" +
                    "<td>" + customer.getTeleNumber() + "</td>" +
                    "<td><a href='/BCUpdate?id=" + customer.getCustomerID() + "'>Edit</a></td>" +
                    "<td><a href='/BCDelete?id=" + customer.getCustomerID() + "'>Delete</a></td>" +
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

        } catch (SQLException e) {
            e.printStackTrace();
            out.write("<tr><td colspan=\"9\">Error fetching customers</td></tr>");
        }

        out.close();
    }

    
}