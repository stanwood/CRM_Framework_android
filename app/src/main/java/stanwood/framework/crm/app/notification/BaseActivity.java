package stanwood.framework.crm.app.notification;


import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import stanwood.framework.crm.Crm;
import stanwood.framework.crm.app.SimpleAppTracker;


/**
 * Exemplar implementation. Same can be done with Fragments.
 * The important part is to check if there are any inApp messages to be displayed for the current screen.
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected abstract String getScreenName();

    @Override
    public void onStart() {
        super.onStart();
        if (!TextUtils.isEmpty(getScreenName())) {
            //Check if there are any screens to be displayed for specific screen
            Crm.getInstance().displayInAppIfAny(this, getScreenName());
            SimpleAppTracker.instance().trackScreenView(getScreenName());
        }
    }
}
