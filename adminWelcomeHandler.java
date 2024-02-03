import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * The adminWelcomeHandler class implements the HttpHandler interface to handle HTTP requests.
 * It provides a welcome page for the admin user.
 */
public class adminWelcomeHandler implements HttpHandler {

    /**
     * Handles the HTTP request and displays the admin welcome page if the request method is GET.
     *
     * @param he the HttpExchange object containing the request and response objects
     * @throws IOException if an input or output error occurs
     */
    @Override
    public void handle(HttpExchange he) throws IOException {
        if ("GET".equals(he.getRequestMethod())) {
            showOptionsPage(he);
        }
    }

    /**
     * Displays the admin welcome page with options for viewing food products, food items, and customers.
     *
     * @param he the HttpExchange object containing the request and response objects
     *@throws IOException if an input or output error occurs
     */
    private void showOptionsPage(HttpExchange he) throws IOException {
        he.sendResponseHeaders(200, 0);
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(he.getResponseBody()));

        // Admin Welcome Page
        System.out.println("Admin Welcome Page");

        out.write(
                "<html>" +
                    "<head>" +
                        "<title>Admin Welcome Page</title>" +
                        "<link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css\">" +
                        "<style>"+
                        "body{"+
                        "font-family: Montserrat, sans-serif;" +
                        "background-image: linear-gradient(to bottom right, #2AF598, #08AEEA);"+
                        "height: 100vh;"+
                        "display: flex;" +
                        "justify-content: center;" +
                    "}"+
                    ".container{"+
                        "text-align: center;"+
                        "flex-direction: column;" +
                        "width:80%;" +
                        "margin-top: 50px;" + 
                    "}"+   
                    ".container h2{"+
                        "margin-top: 70px;"+
                    "}"+
                    ".container h1{"+
                        "margin: 30px 0 70px;"+
                        "font-weight: 600;"+
                    "}"+
                    ".container ul {" +
                        "list-style-type: none;" + 
                        "padding: 0;" +
                    "}"+
                    ".container .row{"+
                        "display: flex;"+
                        "justify-content: center;"+
                        "align-items: center;"+
                        "text-align: center;"+
                    "}"+
                    ".innerCon {" +
                        "background-color: rgba(255, 255, 255, 0.4);" +
                        "margin:20px;"+ 
                        "width:100px;"+
                        "text-align: center;" +
                        "display: flex;" +
                        "flex-direction: column;" +
                        "justify-content: center;" +
                        "align-items: center;" +
                        "height:200px;" +
                        "border-radius: 40px;"+
                        "border: 2px solid white;"+
                    "}"+
                    ".innerCon:hover {"+
                        "background-color: white;"+
                    "}"+
                        "</style>"+
                    "</head>" +
                    "<body>" +
                        "<div class=\"container mt-1\">" +
                            "<h2 >Hello, Admin!</h2>" +
                            "<h1 >Welcome to the Wholesale Food Store</h1>"+
                            "<div class=\"row\">"+
                                "<div class=\"col-md-3 innerCon\">"+
                                    "<ul>"+
                                        "<li><a href=\"/foodProductH\">View Food Products</a></li>"+
                                    "</ul>"+
                                "</div>"+
                                "<div class=\"col-md-3 innerCon\">"+
                                    "<ul>"+
                                        "<li><a href=\"/foodItemH\">View Food Items</a></li>"+
                                    "</ul>"+
                                "</div>"+
                                "<div class=\"col-md-3 innerCon\">"+
                                    "<ul>"+
                                        "<li><a href=\"/customerH\">View Customers</a></li>"+
                                    "</ul>"+
                                "</div>"+
                            "</div>"+
                            "<p class=\"mt-5\"><a href='/'>Logout</a></p>" +
                        "</div>" +
                        "<script src=\"https://code.jquery.com/jquery-3.5.1.slim.min.js\"></script>"+
                        "<script src=\"https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js\"></script>"+
                        "<script src=\"https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js\"></script>"+
                    "</body>" +
                "</html>");

        out.close();
    }
}
