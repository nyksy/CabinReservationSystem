import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Scanner;
import org.json.*;

public class httpController {

    private static String url = "http://cs.uef.fi/tagrohn-bin/cgi_db.py";
    private static String charset = java.nio.charset.StandardCharsets.UTF_8.name();

    public String[][] getValues(String query, String table, String values) throws IOException {

        String sql = String.format("query=%s&table=%s&values=%s",
            URLEncoder.encode(query, charset),
            URLEncoder.encode(table, charset),
            URLEncoder.encode(values, charset));

        URLConnection connection = new URL(url + "?" + sql).openConnection();
        connection.setRequestProperty("Accept-Charset", charset);
        InputStream response = connection.getInputStream();

        String responseBody;
        try (Scanner scanner = new Scanner(response)) {
            responseBody = scanner.useDelimiter("\\A").next();
        }

        JsonDecompiler dec = new JsonDecompiler();


        return dec.decompile2dArray(responseBody);
    }

    public String[] getHeaders(String param1) throws IOException {
        String query = String.format("query=%s&header=true",
                URLEncoder.encode(param1, charset));

        URLConnection connection = new URL(url + "?" + query).openConnection();
        connection.setRequestProperty("Accept-Charset", charset);
        InputStream response = connection.getInputStream();

        String responseBody;
        try (Scanner scanner = new Scanner(response)) {
            responseBody = scanner.useDelimiter("\\A").next();
        }

        JsonDecompiler dec = new JsonDecompiler();

        return dec.decompileArray(responseBody);
    }
}
