/*
 * Copyright 2018 stanwood Gmbh
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package stanwood.framework.crm.utils;


import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesHelper {


    private static final String PREF_FILE_NAME = "android_crm_pref_file";
    private static final String PUSH_TOKEN_KEY = "push_token_key";
    private static final String USER_ID_KEY = "user_id_key";
    private static final String REQUEST_SENT_KEY = "request_sent_key";
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

    public void savePushToken(String token){
        generalPreferences.edit().putString(PUSH_TOKEN_KEY, token).apply();
    }

    public String getPushToken(){
        return generalPreferences.getString(PUSH_TOKEN_KEY, null);
    }

    public void saveUserId(String userId){
        generalPreferences.edit().putString(USER_ID_KEY, userId).apply();
    }

    public String getUserId(){
        return generalPreferences.getString(USER_ID_KEY, null);
    }

    public void setPushTokenSent(boolean wasSent){
        generalPreferences.edit().putBoolean(REQUEST_SENT_KEY, wasSent).apply();
    }

    public boolean wasPushTokenSent(){
        return generalPreferences.getBoolean(REQUEST_SENT_KEY, false);
    }

}
