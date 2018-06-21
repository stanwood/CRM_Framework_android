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