import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.SQLException;

/**
 * The BCDeleteHandler class implements the HttpHandler interface to handle HTTP requests for deleting business customers.
 * It retrieves the customer ID from the query parameter, attempts to delete the customer, and sends an appropriate response to the client.
 */
public class BCDeleteHandler implements HttpHandler {

    /**
    *  Handles the HTTP request for deleting a business customer.
    *
    * @param he the HttpExchange to be handled
    * @throws IOException if an input or output exception occurs
    */
    @Override
    public void handle(HttpExchange he) throws IOException {
        // Extract customer ID from the query parameter
        String query = he.getRequestURI().getQuery();
        String[] queryParams = query.split("=");
        int customerID = Integer.parseInt(queryParams[1]);

        // Fetch the existing details of the business customer
        CustomerDAO BCD = new CustomerDAO();
        Customer deletedCustomer = null;
        boolean deletionSuccess = false;

        try {
            System.out.println("Attempting to delete customer with ID: " + customerID);
            deletedCustomer = BCD.findCustomer(customerID);

            if (deletedCustomer != null) {
                deletionSuccess = BCD.deleteCustomer(customerID);

                if (deletionSuccess) {
                    System.out.println("Customer successfully deleted");
                } else {
                    System.out.println("Customer deletion failed");
                }
               
        } 
        
        }  catch (SQLException e) {
            e.printStackTrace(); 
        }

        he.sendResponseHeaders(200, 0);
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(he.getResponseBody()));

        // if deletion was successful display details of the deleted customer
        if (deletionSuccess) {
            
            out.write(
                "<html>" +
                "<head>"+
                    "<title>Customer Deleted</title>"+
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
                        "width: 80%;" +
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
                "<h2>Business Customer Successfully Deleted</h2>" +
                "<div class='innerCon'>"+
                "<table>" +
                "<thead>" +
                "<tr class='trow'>" +
                "<th>Customer ID</th>" +
                "<th>Business Name</th>" +
                "<th>Address Line 1</th>" +
                "<th>Address Line 2</th>" +
                "<th>Address Line 3</th>" +
                "<th>Country</th>" +
                "<th>Postcode</th>" +
                "<th>Telephone Number</th>" +
                "</tr>" +
                "</thead>" +
                "<tbody>" +
                "<tr>" +
                "<td>" + deletedCustomer.getCustomerID() + "</td>" +
                "<td>" + deletedCustomer.getBusinessName() + "</td>" +
                "<td>" + deletedCustomer.getAddress().getAddressLine1() + "</td>" +
                "<td>" + deletedCustomer.getAddress().getAddressLine2() + "</td>" +
                "<td>" + deletedCustomer.getAddress().getAddressLine3() + "</td>" +
                "<td>" + deletedCustomer.getAddress().getCountry() + "</td>" +
                "<td>" + deletedCustomer.getAddress().getPostCode() + "</td>" +
                "<td>" + deletedCustomer.getTeleNumber() + "</td>" +
                "</tr>" +
                "</tbody>" +
                "</table>" +
                "</div>"+
                "<p><a href=\"/customerH\">Back to Business Customers</a></p>" +
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
                "<h2>Error deleting business customer</h2>" +
                "<p>Customer not found or deletion failed</p>" +
                "<p><a href=\"/customerH\">Back to Business Customers</a></p>" +
                "</div>"+
                "</body>" +
                "</html>"
            );
        }

        out.close();
    }
}