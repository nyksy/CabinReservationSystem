import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Scanner;

import org.json.*;

/**
 * Luokka HTTP - Tietokantayhteyttä varten
 */
public class httpController {

    //Tietokannan cgi-rajapinnan URL
    private static String url = "http://cs.uef.fi/tagrohn-bin/cgi_db.py";
    //Koodauksessa käytettävä charset
    private static String charset = java.nio.charset.StandardCharsets.UTF_8.name();

    /**
     * Hae tietokannasta tietoa kutsumalla cgi-rajapintaa callCGI metodilla
     *
     * @param query  HTTP query= parametri. Tarkentaa, mitä SQL toimintoa halutaan kutsua
     * @param table  HTTP table= parametri. Tarkentaa mistä taulusta halutaan tietoa
     * @param values HTTP values= parametri. Tarkentaa mitä tietoa halutaan tallettaa insert metodissa.
     * @return Palauttaa 2d array:n, joka sisältää tietokantakyselyn tulokset. Arrayn yksi rivi
     * kuvastaa tietokannan riviä
     * @throws IOException Virhe HTTP yhteydessä
     */
    public String[][] useDB(String query, String table, String values) throws IOException {

        String sql = String.format("query=%s&table=%s&values=%s",
                URLEncoder.encode(query, charset),
                URLEncoder.encode(table, charset),
                URLEncoder.encode(values, charset));
        JsonDecompiler dec = new JsonDecompiler();
        return dec.decompile2dArray(callCGI(sql));
    }

    /**
     * Metodi tietokannan taulujen kolumnien nimien hakemiselle
     *
     * @param table Tietokannan taulun nimi, jonka kolumnien nimet halutaan
     * @return Palauttaa 1d array:n, joka sisältää kolumnien nimet
     * @throws IOException virhe HTTP yhteydessä
     */
    public String[] getHeaders(String table) throws IOException {
        String sql = String.format("query=select&table=%s&header=true",
                URLEncoder.encode(table, charset));

        //Luo uusi JsonDecompiler ja palauta purettu viesti
        JsonDecompiler dec = new JsonDecompiler();
        return dec.decompileArray(callCGI(sql));
    }

    /**
     * Metodi spesifien tietokantakyselyiden juoksuttamiselle. Toimii ainoastaan SELECT kyselyillä.
     *
     * @param statement SQL SELECT Kysely, esim. "SELECT Asiakas_ID FROM Asiakas"
     * @return Kyselyn tulokset String matriisina
     * @throws IOException Yhteysvirhe
     */
    public String[][] runSQL(String statement) throws IOException {
        String sql = String.format("query=any&auth=XyzzyxHUI420&sql=%s",
                URLEncoder.encode(statement, charset));

        //Luo uusi JsonDecompiler ja palauta purettu viesti
        JsonDecompiler dec = new JsonDecompiler();
        return dec.decompile2dArray(callCGI(sql));
    }

    /**
     * Metodi, joka kutsuu CGI rajapintaa URL-yhteydellä. Rajapinta palauttaa kyselyn tuloksen JSON-muodossa.
     *
     * @param sql Valmiiksi muotoiltu kysely
     * @return Kyselyn tulos JSON muodossa
     * @throws IOException Yhteysvirhe
     */
    private String callCGI(String sql) throws IOException {
        //Hae HTTP yhteydellä parametrien määrittelemän kyselyn tulokset JSON muodossa
        URLConnection connection = new URL(url + "?" + sql).openConnection();
        connection.setRequestProperty("Accept-Charset", charset);
        InputStream response = connection.getInputStream();

        String responseBody;
        try (Scanner scanner = new Scanner(response)) {
            responseBody = scanner.useDelimiter("\\A").next();
        }
        return responseBody;
    }
}
