import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.SQLException;
import java.util.Map;

/**
 * This class handles HTTP requests for updating business customers.
 * It uses the HttpHandler interface from the com.sun.net.httpserver package.
 * The handle method is overridden to process incoming HTTP exchange objects.
 * It parses the form data, updates the customer details in the database,
 * and sends a response back to the client.
 *
 */
public class BCUProcessHandler implements HttpHandler {

    /**
    * This method is called whenever an HTTP request comes in.
    * It tries to parse the form data from the HTTP exchange, update the customer details in the database,
    * and send a success message back to the client.
    * If there's an SQLException or IllegalArgumentException, it prints the stack trace and sends an error message back to the client.
    *
    * @param he The HTTP exchange object containing the request and response information.
    * @throws IOException If an I/O error occurs.
    */
    @Override
    public void handle(HttpExchange he) throws IOException {
        System.out.println("Customer Update Process Handler Called");

        he.sendResponseHeaders(200, 0);
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(he.getResponseBody()));

        try {
            // Parse form data
            Map<String, String> formData = Util.parseFormData(he);
            System.out.println("This is the data "+formData);

            // Get details from the user
            int customerID = Integer.parseInt(formData.get("customerID"));
            String businessName = formData.get("businessName");
            String addressLine1 = formData.get("addressLine1");
            String addressLine2 = formData.get("addressLine2");
            String addressLine3 = formData.get("addressLine3");
            String country = formData.get("country");
            String postCode = formData.get("postCode");
            String teleNumber = formData.get("teleNumber");

            try {
                validateTeleNumber(teleNumber);
            } catch (IllegalArgumentException e) {

                BufferedWriter errorOut = new BufferedWriter(new OutputStreamWriter(he.getResponseBody()));

                errorOut.write(
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
                "<h2>Error adding customer</h2>" +
                "<p>Please check the entered values and try again</p>"+
                "<p><a href=\"/customerH\">Back to Customers</a></p>" +
                "</div>"+
                "</body>" +
                "</html>"
            );

            errorOut.close();

                throw new IllegalArgumentException("Invalid telephone number. Must be a 10-digit number.");
            }

            // Update the business customer in the database
            CustomerDAO BCD = new CustomerDAO();
            Address address = new Address(addressLine1, addressLine2, addressLine3, country, postCode);
            Customer updatedCustomer = new Customer(customerID, businessName, address, teleNumber);
            boolean customerUpdated = BCD.updateCustomer(updatedCustomer);

            if(customerUpdated){
                out.write(
                "<html>" +
                "<head>"+
                    "<title>Customer Updated</title>"+
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
                "<h2>Business Customer Successfully Updated</h2>" +
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
                "<td>" + updatedCustomer.getCustomerID() + "</td>" +
                "<td>" + updatedCustomer.getBusinessName() + "</td>" +
                "<td>" + updatedCustomer.getAddress().getAddressLine1() + "</td>" +
                "<td>" + updatedCustomer.getAddress().getAddressLine2() + "</td>" +
                "<td>" + updatedCustomer.getAddress().getAddressLine3() + "</td>" +
                "<td>" + updatedCustomer.getAddress().getCountry() + "</td>" +
                "<td>" + updatedCustomer.getAddress().getPostCode() + "</td>" +
                "<td>" + updatedCustomer.getTeleNumber() + "</td>" +
                "</tr>" +
                "</tbody>" +
                "</table>" +
                "</div>"+
                "<a href=\"/customerH\">Back to Business Customers</a>" +
                "</div>"+
                "</body>" +
                "</html>"
            );
            }else {
                throw new SQLException("Failed to update Customer");
            }
              

        } catch (SQLException e) {
            e.printStackTrace(); 

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
                "<h1>Error updating business customer</h1>" +
                "<p>Please check the entered values and try again</p>"+
                "<p><a href=\"/customerH\">Back to Business Customers</a></p>" +
                "</div>"+
                "</body>" +
                "</html>"
            );

        }

        out.close();
    }

    private void validateTeleNumber(String teleNumber) throws IllegalArgumentException {
        if (teleNumber == null || teleNumber.length() != 10 || !teleNumber.matches("\\d{10}")) {
            throw new IllegalArgumentException("Invalid telephone number. Must be a 10-digit number.");
        }
    }
}