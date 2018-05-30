package stanwood.framework.crm.app;


import android.app.Application;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import io.stanwood.framework.analytics.BaseAnalyticsTracker;
import io.stanwood.framework.analytics.fabric.FabricTracker;
import io.stanwood.framework.analytics.fabric.FabricTrackerImpl;
import io.stanwood.framework.analytics.firebase.FirebaseTracker;
import io.stanwood.framework.analytics.firebase.FirebaseTrackerImpl;
import io.stanwood.framework.analytics.generic.Tracker;
import io.stanwood.framework.analytics.testfairy.TestfairyTracker;
import io.stanwood.framework.analytics.testfairy.TestfairyTrackerImpl;

/**
 * Implementation of tracking library
 */
public class SimpleAppTracker extends BaseAnalyticsTracker {
    private static SimpleAppTracker instance;

    private SimpleAppTracker(@NonNull Application application, @NonNull FabricTracker fabricTracker, @NonNull FirebaseTracker firebaseTracker,
                             @NonNull TestfairyTracker testfairyTracker, @Nullable Tracker... optional) {
        super(application, fabricTracker, firebaseTracker, testfairyTracker, optional);
    }

    public static synchronized void init(Application application) {
        if (instance == null) {
            instance = new SimpleAppTracker(application,
                    FabricTrackerImpl.builder(application).build(),
                    FirebaseTrackerImpl.builder(application)
                            .setExceptionTrackingEnabled(true)
                            .build(),
                    TestfairyTrackerImpl.builder(application, "ADD YOUR KEY HERE").build());
        }
    }

    public static SimpleAppTracker instance() {
        if (instance == null) {
            throw new IllegalArgumentException("Call init() first!");
        }
        return instance;
    }

}