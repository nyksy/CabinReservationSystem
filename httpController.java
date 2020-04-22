import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Scanner;
import org.json.*;

public class httpController {
    //Testikommentti
    public static void main(String[] args) {
        try {
            getHTTP();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void getHTTP() throws IOException {
        String url = "http://cs.uef.fi/tagrohn-bin/testi2.py";
        String charset = java.nio.charset.StandardCharsets.UTF_8.name();  // Or in Java 7 and later, use the constant: java.nio.charset.StandardCharsets.UTF_8.name()
        String param1 = "Kurssi";

        String query = String.format("query=%s",
            URLEncoder.encode(param1, charset));

        URLConnection connection = new URL(url + "?" + query).openConnection();
        connection.setRequestProperty("Accept-Charset", charset);
        InputStream response = connection.getInputStream();

        System.out.println(response);
        try (Scanner scanner = new Scanner(response)) {
            String responseBody = scanner.useDelimiter("\\A").next();
            System.out.println(responseBody);
        }
    }
}
