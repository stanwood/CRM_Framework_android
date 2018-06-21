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
