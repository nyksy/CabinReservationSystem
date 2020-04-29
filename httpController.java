import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Scanner;

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
     * @return Palauttaa 2d array:n, joka sisältää tietokantakyselyn tulokset. Arrayn yksi rivi
     * kuvastaa tietokannan riviä
     * @throws IOException Yhteys/tietokantavirhe
     */
    public String[][] getValues(String query, String table) throws IOException {
        //testi
        String sql = String.format("query=%s&table=%s",
                URLEncoder.encode(query, charset),
                URLEncoder.encode(table, charset));
        JsonDecompiler dec = new JsonDecompiler();
        return dec.decompile2dArray(callCGI(sql));
    }

    /**
     * Talleta tietokantaan tietoa cgi-rajapintaa hyödyntäen
     *
     * @param table Kohteena oleva taulu
     * @param values Talletettavat arvot
     * @throws IOException Yhteys/tietokantavirhe
     */
    public void setValues(String table, String values) throws IOException {
        String sql = String.format("query=insert&table=%s&values=%s",
                URLEncoder.encode(table, charset),
                URLEncoder.encode(values, charset));
        String responseBody = callCGI(sql);
        System.out.println(responseBody);
    }

    /**
     * Päivitä tietokantaan talletettuja tietoja cgi-rajapintaa hyödyntäen
     *
     * @param table Kohteena oleva taulu
     * @param values Päivitettävät arvot
     * @param identifier Kohteena oleva tietue
     * @throws IOException Yhteys/tietokantavirhe
     */
    public void updateValues(String table, String values, String identifier) throws IOException {
        String sql = String.format("query=update&table=%s&values=%s&identifier=%s",
                URLEncoder.encode(table, charset),
                URLEncoder.encode(values, charset),
                URLEncoder.encode(identifier, charset));
        String responseBody = callCGI(sql);
        System.out.println(responseBody);
    }

    /**
     * Poista tietokantaan talletettuja tietoja cgi-rajapintaa hyödyntäen
     *
     * @param table Kohteena oleva taulu
     * @param values Poistettavan tietueen Primary Key
     * @param identifier Lisäarvo palveluvarausta poistettaessa (Palvelu_ID)
     * @throws IOException Yhteys/tietokantavirhe
     */
    public void deleteValues(String table, String values, String identifier) throws IOException {
        String sql = String.format("query=delete&table=%s&values=%s&identifier=%s",
                URLEncoder.encode(table, charset),
                URLEncoder.encode(values, charset),
                URLEncoder.encode(identifier, charset));
        String responseBody = callCGI(sql);
        System.out.println(responseBody);
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
