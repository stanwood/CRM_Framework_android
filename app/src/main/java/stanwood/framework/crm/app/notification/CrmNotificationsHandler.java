package stanwood.framework.crm.app.notification;


import android.app.Application;
import android.app.NotificationManager;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import io.stanwood.framework.analytics.BaseAnalyticsTracker;
import stanwood.framework.crm.app.MainActivity;
import stanwood.framework.crm.BaseNotificationHandler;

/**
 * Concrete implementation of @{@link BaseNotificationHandler}
 */
public class CrmNotificationsHandler extends BaseNotificationHandler {


    public CrmNotificationsHandler(Application application, BaseAnalyticsTracker baseAnalyticsTracker) {
        super(application, baseAnalyticsTracker);
    }

    @Override
    public void createNotification() {
        //Creating default notification chanel based on crm.xml configuration
        NotificationManager notificationManager = createDefaultNotificationChannel();
        //Specifying intent of target activity
        Intent intent = new Intent(getContext(), MainActivity.class);
        //Creating default notification builder
        NotificationCompat.Builder builder = createDefaultNotificationBuilder(intent, getTitle(), getMessage());
        //Sending notification
        sendNotification(notificationManager, builder);
    }
}
