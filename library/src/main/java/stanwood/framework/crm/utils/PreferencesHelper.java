package stanwood.framework.crm.utils;


import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesHelper {


    public static final String PREF_FILE_NAME = "android_crm_pref_file";
    private final SharedPreferences generalPreferences;
    private final Application application;


    public PreferencesHelper(Application application) {
        this.application = application;
        generalPreferences = application.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
    }

    public void saveInApp(String key, String value) {
        generalPreferences.edit().putString(key, value).apply();
    }

    public String getInApp(String key) {
        return generalPreferences.getString(key, null);
    }
}
