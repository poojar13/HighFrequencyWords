package rathore.pooja.viacom18.utils;

public class AppUtils {
    public static String[] FILTER_ARRAY = {"of", "the", "The", "a", "an", "with", "he", "she", "it", "they", "them", "in", "also", "because", "us", "and", "all"};
    public static String BASE_URL = "https://";

    public static String[] getUniqueKeys(String[] keys) {
        String[] uniqueKeys = new String[keys.length];
        uniqueKeys[0] = keys[0];
        int uniqueKeyIndex = 1;
        boolean keyAlreadyExists = false;
        for (int i = 1; i < keys.length; i++) {
            for (int j = 0; j <= uniqueKeyIndex; j++) {
                if (keys[i].equals(uniqueKeys[j])) {
                    keyAlreadyExists = true;
                }
            }
            if (!keyAlreadyExists) {
                uniqueKeys[uniqueKeyIndex] = keys[i];
                uniqueKeyIndex++;
            }
            keyAlreadyExists = false;
        }
        return uniqueKeys;
    }
}
