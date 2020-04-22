import org.json.JSONArray;
import org.json.JSONObject;

public class JsonDecompiler {

    public void decompile(String json) {
        JSONArray mJsonArray = new JSONArray(json);
        JSONObject mJsonObject = new JSONObject();
        for (int i = 0; i < mJsonArray.length(); i++) {
            mJsonObject = mJsonArray.getJSONObject(i);
            mJsonObject.getString("0");
            mJsonObject.getString("id");
            mJsonObject.getString("1");
            mJsonObject.getString("name");
        }
    }
}
