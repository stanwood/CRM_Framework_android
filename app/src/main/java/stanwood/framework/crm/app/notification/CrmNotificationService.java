package stanwood.framework.crm.app.notification;


import android.support.annotation.NonNull;

import stanwood.framework.crm.app.SimpleAppTracker;
import stanwood.framework.crm.BaseMessagingService;
import stanwood.framework.crm.BaseNotificationHandler;

/**
 * Concrete implementation of @{@link BaseMessagingService}
 */
public class CrmNotificationService extends BaseMessagingService {


    @NonNull
    @Override
    public BaseNotificationHandler createBaseNotificationHandler() {
        return new CrmNotificationsHandler(getApplication(), SimpleAppTracker.instance());
    }
}
