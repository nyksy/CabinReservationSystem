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
     * Metodi json viestin purkamiseksi ja tallettamiseksi String[x][y] muotoon
     *
     * @param json JSON viesti, "Array of Arrays" - muodossa
     * @return 2d Array, joka sisältää JSON viestin datan String muodossa
     */
    public String[][] decompile2dArray(String json) {
        JSONArray mJsonArray = new JSONArray(json);
        String[][] returnList = new String[mJsonArray.length()][mJsonArray.getJSONArray(0).length()];
        for (int x = 0; x < mJsonArray.length(); x++) {
            for (int y = 0; y < mJsonArray.getJSONArray(x).length(); y++) {
                returnList[x][y] = mJsonArray.getJSONArray(x).get(y).toString();
            }
        }
        return returnList;
    }

    /**
     * Metodi 1d Arrayn dekoodausta varten
     * @param json JSON viesti Array muodossa
     * @return Yksiulotteinen Array, joka sisältää JSON viestin sisällön
     */
    public String[] decompileArray(String json) {
        JSONArray mJsonArray = new JSONArray(json);
        String[] returnList = new String[mJsonArray.length()];
        for (int x = 0; x < returnList.length; x++) {
            returnList[x] = mJsonArray.get(x).toString();
        }
        return returnList;
    }
}
