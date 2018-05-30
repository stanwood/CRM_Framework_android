package stanwood.framework.crm.utils;

import android.net.Uri;
import android.support.annotation.NonNull;

import java.util.Set;
import java.util.TreeMap;

public class UrlUtils {

    @NonNull
    public static TreeMap<String, String> getQueryMap(String query) {
        TreeMap<String, String> map = new TreeMap<>();
        try {
            Uri uri = Uri.parse(query);
            Set<String> keys = uri.getQueryParameterNames();
            if (keys != null) {
                for (String key : keys) {
                    String value = uri.getQueryParameter(key);
                    map.put(key, value);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}