package stanwood.framework.crm;


import android.app.Activity;
import android.app.Application;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import java.util.HashMap;

import io.stanwood.framework.analytics.BaseAnalyticsTracker;
import stanwood.framework.crm.ui.InAppDialogFragment;
import stanwood.framework.crm.utils.PreferencesHelper;

/**
 * Class responsible for passing a singleton tracker to a CRM library.
 * Used for tracking the push token.
 */
public class Crm {

    private static Crm instance;
    private final BaseAnalyticsTracker baseAnalyticsTracker;
    private final PreferencesHelper preferencesHelper;
    private final Application application;

    HashMap<String, Class<? extends InAppDialogFragment>> dialogDesigns = new HashMap<>();

    private static final String TAG = "CRM";

    private Crm(@NonNull Application application, @NonNull BaseAnalyticsTracker baseAnalyticsTracker) {
        this.baseAnalyticsTracker = baseAnalyticsTracker;
        this.application = application;
        this.preferencesHelper = new PreferencesHelper(application);
    }

    public static void init(@NonNull Application application, @NonNull BaseAnalyticsTracker baseAnalyticsTracker) {
        instance = new Crm(application, baseAnalyticsTracker);
    }

    /**
     * @return instance of CRM singleton
     */
    public static Crm getInstance() {
        return instance;
    }

    void saveInApp(String targetScreenName, String link) {
        preferencesHelper.saveInApp(targetScreenName, link);
    }

    private String getInApp(String screenName) {
        return preferencesHelper.getInApp(screenName);
    }

    /**
     * Add a custom dialog design for specific screen
     * Mind that dialogFragment needs to extend {@code InAppDialogFragment}
     *
     * @param screenName
     * @param dialogFragment
     */
    public void addDialogDesign(String screenName, Class<? extends InAppDialogFragment> dialogFragment) {
        dialogDesigns.put(screenName, dialogFragment);
    }

    /**
     * Check if there is any dialog to be displayed for specified screenName.
     * If there is then method displays it.
     *
     * @param activity
     * @param screenName
     */
    public void displayInAppIfAny(@Nullable Activity activity, @NonNull String screenName) {
        displayInAppIfAny(activity, screenName, null);
    }

    /**
     * Check if there is any dialog to be displayed for specified screenName.
     * If there are then method displays it using specified dialogFragment.
     *
     * @param activity
     * @param screenName
     * @param dialogFragment
     */
    public void displayInAppIfAny(@Nullable Activity activity, @NonNull String screenName, @Nullable InAppDialogFragment dialogFragment) {
        //TODO remove this only needed for testing
        String link = "https://www.google.pl";//getInApp(screenName);
        if (!TextUtils.isEmpty(link)) {
            Log.v(TAG, "Has inapp for " + screenName + " : " + link);
        }

        if (activity != null && !activity.isFinishing()) {
            FragmentTransaction ft = activity.getFragmentManager().beginTransaction();
            Fragment prev = activity.getFragmentManager().findFragmentByTag("inAppDialog");
            if (prev != null) {
                ft.remove(prev);
            }
            ft.addToBackStack(null);
            ft.commit();

            //Handle case for no custom dialog
            if (dialogFragment == null) {
                if (dialogDesigns.containsKey(screenName)) {
                    try {
                        Log.v(TAG, "Has custom inapp design for " + screenName + " : " + link);
                        Class<? extends InAppDialogFragment> dialogClass = dialogDesigns.get(screenName);
                        dialogFragment = dialogClass.newInstance();
                    } catch (InstantiationException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                } else {
                    dialogFragment = new InAppDialogFragment();
                }
            }

            if (dialogFragment != null) {
                Bundle bundle = new Bundle();
                bundle.putString(InAppDialogFragment.LINK_KEY, link);
                dialogFragment.setArguments(bundle);

                dialogFragment.show(activity.getFragmentManager(), "inAppDialog");
            }
        }
    }

    BaseAnalyticsTracker getBaseAnalyticsTracker() {
        return baseAnalyticsTracker;
    }
}
