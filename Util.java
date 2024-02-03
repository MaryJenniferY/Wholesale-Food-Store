import com.sun.net.httpserver.HttpExchange;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for parsing form data from an HTTP request.
 */
public class Util {

    /**
    * Parses form data from an HTTP request.
    * 
    * @param he HttpExchange object representing the HTTP request.
    * @return A map containing the parsed form data. The keys are the form field names and the values are the corresponding field values.
    * @throws IOException if an I/O error occurs.
    */
    public static Map<String, String> parseFormData(HttpExchange he) throws IOException {
        Map<String, String> result = new HashMap<>();

        InputStreamReader isr = new InputStreamReader(he.getRequestBody(), "utf-8");
        BufferedReader br = new BufferedReader(isr);
        String formData = br.readLine();

        if (formData != null) {
            String[] pairs = formData.split("&");
            for (String pair : pairs) {
                String[] keyValue = pair.split("=");
                if (keyValue.length == 2) {
                    String key = keyValue[0];
                    String value = keyValue[1].replace('+', ' '); // Replace '+' with space so its stored/retrieved correctly
                    result.put(key, value);
                }
            }
        }
        

        return result;
    }
}