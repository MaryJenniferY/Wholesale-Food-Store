//LOGIN DETAILS
// username : admin
// password : admin

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This is the main class of the application.
 * It sets up the HTTP server and handles various requests.
 */
public class Main {

    /**
    * The port number on which the server listens.
    */
    static final private int PORT = 8080;
    
    /**
    * The main method of the application.
    * It creates the HTTP server, sets up the request handlers, starts the server, and runs the menu display logic.
    * @param args Command-line arguments (not used in this application)
    * @throws IOException if an error occurs during server creation or start.
    * @throws SQLException if an error occurs during database operations.
    */
    public static void main(String[] args) throws IOException, SQLException{
        
        HttpServer server = HttpServer.create(new InetSocketAddress(PORT),0);
        server.createContext("/", new loginHandler());
        server.createContext("/adminWelcome", new adminWelcomeHandler());
        server.createContext("/foodProductH", new foodProductHandler());
        server.createContext("/customerH", new customerHandler());
        server.createContext("/FPUpdate", new FPUpdateHandler());
        server.createContext("/FPUProcess", new FPUProcessHandler());
        server.createContext("/FPAdd", new FPAddHandler());
        server.createContext("/FPAProcess", new FPAProcessHandler());
        server.createContext("/BCAdd", new BCAddHandler());
        server.createContext("/BCAProcess", new BCAProcessHandler()); 
        server.createContext("/BCUpdate", new BCUpdateHandler());   
        server.createContext("/BCUProcess", new BCUProcessHandler());
        server.createContext("/FPDelete", new FPDeleteHandler());
        server.createContext("/BCDelete", new BCDeleteHandler());
        server.createContext("/foodItemH", new foodItemHandler());    
        server.createContext("/foodItemAdd", new FIAddHandler());   
        server.createContext("/FIAProcess", new FIAProcessHandler());  
        server.createContext("/foodItemDelete", new FIDeleteHandler());    
        server.createContext("/ExpiredFP", new ExpiredFPHandler());
        server.createContext("/shoppingBasket", new ShoppingBasketHandler());
        
        
        // Create an ExecutorService with a fixed thread pool so that background tasks such as menu console can run
        // without blocking the main thread of the application and making the application slow
        ExecutorService executorService = Executors.newFixedThreadPool(1);

        // Menu Display logic in a separate thread
        executorService.execute(() -> { 
            menu menu = new menu();
            try {
                menu.menuDisplay();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        server.setExecutor(null);
        server.start();
        System.out.println("The server is listening on port "+ PORT);

        //Gracefully shut down the ExecutorService
        Runtime.getRuntime().addShutdownHook(new Thread(executorService::shutdown));

    }    
    
}






