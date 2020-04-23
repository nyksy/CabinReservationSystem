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
     */
    public String[][] decompileArray(String json) {
        JSONArray mJsonArray = new JSONArray(json);
        String[][] returnList = new String[mJsonArray.length()][mJsonArray.getJSONArray(0).length()];
        for (int x = 0; x < mJsonArray.length(); x++) {
            for (int y = 0; y < mJsonArray.getJSONArray(x).length(); y++) {
                returnList[x][y] = mJsonArray.getJSONArray(x).get(y).toString();
            }
        }

        for (int x = 0; x < returnList.length; x++) {
            for (int y = 0; y < returnList[x].length; y++) {
                System.out.println(returnList[x][y]);
            }
        }
        return returnList;
    }
}
