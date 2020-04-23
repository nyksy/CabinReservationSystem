import org.json.JSONArray;
import java.util.ArrayList;

/**
 * Luokka rajapinnan JSON vastauksen dekoodaamiseen ja palauttamiseen ArrayList<ArrayList<String>>
 * muodossa
 * <p>
 * Dependency: json-20190722.jar
 */
public class JsonDecompiler {

    /**
     * Metodi json viestin purkamiseksi ja tallettamiseksi ArrayList<String[]> muotoon
     * Ulkoinen ArrayList sisältää String[] taulukkoja, jotka kuvastavat tietokannan rivejä.
     * Sisäinen String[] sisältää tietokannasta saadun rivin tiedot attribuutti per indeksi.
     *
     * @param json JSON viesti, "Array of Arrays" - muodossa
     */
    public ArrayList<String[]> decompileArray(String json) {
        JSONArray mJsonArray = new JSONArray(json);
        ArrayList<String[]> returnList = new ArrayList<>();
        for (int i = 0; i < mJsonArray.length(); i++) {
            String[] tempArray = new String[mJsonArray.getJSONArray(i).length()];
            for (int j = 0; j < mJsonArray.getJSONArray(i).length(); j++) {
                tempArray[j] = mJsonArray.getJSONArray(i).get(j).toString();
            }
            returnList.add(tempArray);
        }
        //Testicommit
        System.out.println("Palautuslistan koko " + returnList.size());
        for (int i = 0; i < returnList.size(); i++) {
            for (int j = 0; j < returnList.get(i).length; j++) {
                String[] temp = returnList.get(i);
                System.out.println(temp[j]);
            }
        }
        return returnList;
    }
}
