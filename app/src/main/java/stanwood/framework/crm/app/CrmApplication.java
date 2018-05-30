package stanwood.framework.crm.app;


import android.app.Application;

import stanwood.framework.crm.Crm;
import stanwood.framework.crm.app.notification.CustomInAppDialogFragment;

public class CrmApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        //Initialization of tracking library
        SimpleAppTracker.init(this);
        //Adding user tracking for campaigns purposes
        SimpleAppTracker.instance().trackUser("1234", "test_account@stanwood.de", null);

        //Needs initialization for tracking push token
        Crm.init(this, SimpleAppTracker.instance());

        //Optional code for specifying different inApp designs based on screen
        Crm.getInstance().addDialogDesign("main_view", CustomInAppDialogFragment.class);
    }
}
