import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonDecompiler {

    public void decompile(String json) {
        JSONArray mJsonArray = new JSONArray(json);
        ArrayList<ArrayList<String>> PalautusLista = new ArrayList<>();
        ArrayList<String> Valilista = new ArrayList<>();
        System.out.println(mJsonArray.length());
        System.out.println(mJsonArray.getJSONArray(0).length());
        for(int i = 0; i < mJsonArray.length(); i++){
            for(int j = 0; j < mJsonArray.getJSONArray(i).length(); j++) {
                Valilista.add(mJsonArray.getJSONArray(i).get(j).toString());
            }
        }
        PalautusLista.add(Valilista);

        System.out.println("Palautuslistan koko " + PalautusLista.size());
        for (int i = 0; i < PalautusLista.size(); i++) {
            for (int j = 0; j < PalautusLista.get(i).size(); j++) {
                System.out.println(PalautusLista.get(i).get(j));
            }
        }
    }
}
