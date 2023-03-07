package SwitchAnalyzer.miscellaneous;

import com.google.gson.Gson;

public class JSONConverter {
    private static Gson gson = new Gson();
    public static String toJSON(Object object) {
        return gson.toJson(object);
    }

    public static <T> T fromJSON(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }
}
