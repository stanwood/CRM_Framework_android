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

package stanwood.framework.crm;


import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import io.stanwood.framework.analytics.generic.TrackerParams;
import io.stanwood.framework.analytics.generic.TrackingEvent;
import io.stanwood.framework.analytics.generic.TrackingKey;

public class FirebasePushTokenService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";
    private final String PUSH_EVENT = "event_push_token";


    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);
        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String token) {
        Crm instance = Crm.getInstance();

        if (instance == null) {
            throw new IllegalStateException("Make sure you initialize CRM library in your application Crm.init()");
        }

        instance.getAnalyticsTracker().trackEvent(TrackerParams.builder(TrackingEvent.IDENTIFY_USER)
                .addCustomProperty(TrackingKey.PUSH_TOKEN, token)
                .build());

        instance.getAnalyticsTracker().trackEvent(TrackerParams.builder(PUSH_EVENT)
                .addCustomProperty(TrackingKey.PUSH_TOKEN, token)
                .build());
    }
}
