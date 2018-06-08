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
