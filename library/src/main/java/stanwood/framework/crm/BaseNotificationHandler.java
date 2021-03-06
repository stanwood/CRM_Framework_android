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


import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;

import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import io.stanwood.framework.analytics.BaseAnalyticsTracker;

/**
 * Class responsible for handling data extracted from the push notification,
 * specifying notification UI and displaying the message.
 * Each application needs to override {@code createNotification()} method in order to specify
 * the correct intent, channel, message format for particular notification.
 */
public abstract class BaseNotificationHandler {


    private String title;
    private String message;
    private String bigMessage;
    private Map<String, String> params;
    private String link;
    private int messageId;
    private RemoteMessage remoteMessage;

    private Application application;

    private BaseAnalyticsTracker baseAnalyticsTracker;



    public BaseNotificationHandler(Application application, BaseAnalyticsTracker baseAnalyticsTracker) {
        this.application = application;
        this.baseAnalyticsTracker = baseAnalyticsTracker;
    }

    public void setNotificationData(String title, String message, String bigMessage, Map<String, String> params, int messageId, String link) {
        this.title = title;
        this.message = message;
        this.bigMessage = bigMessage;
        this.params = params;
        this.messageId = messageId;
        this.link = link;
    }

    public void setNotificationData(String title, String message, Map<String, String> params, int messageId, String link) {
        this.title = title;
        this.message = message;
        this.params = params;
        this.messageId = messageId;
        this.link = link;
    }

    /**
     * Method called after receiving notification and extracting the data;
     * It is apps responsibility to display the notification message and create the intent.
     * For sample implementation please check the sample app @{SimpleAppNotificationsHandler}
     */
    public abstract void createNotification();


    /**
     * Creating default notification channel based on the crm.xml
     */
    protected NotificationManager createDefaultNotificationChannel() {
        NotificationManager notificationManager = (NotificationManager) application.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(application.getString(R.string.default_notification_channel_id),
                    application.getString(R.string.default_notification_channel_description),
                    NotificationManager.IMPORTANCE_HIGH);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
        return notificationManager;
    }

    /**
     * Exemplar default notification builder.
     * Normally apps will create their own builder based on the local specification.
     *
     * @param intent  Intent of the target activity
     * @param title   notification title
     * @param message notification body message
     * @return notification builder
     */

    protected NotificationCompat.Builder createDefaultNotificationBuilder(@NonNull Intent intent, String title, String message) {
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(application, 0 /* request code */, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        return new NotificationCompat.Builder(application, application.getString(R.string.default_notification_channel_id))
                .setDefaults(Notification.DEFAULT_ALL)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(message)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setCategory(Notification.CATEGORY_RECOMMENDATION)
                .setPriority(Notification.PRIORITY_MAX)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);
    }

    /**
     * Method responsible for notifying notificationManager about new push message
     *
     * @param notificationManager notification handler
     * @param builder             notification builder with defined push notifications
     */
    protected void sendNotification(NotificationManager notificationManager, NotificationCompat.Builder builder) {
        try {
            if (notificationManager != null) {
                notificationManager.notify(messageId, builder.build());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method for obtaining title of the push notifications.
     * Title is assigned in @{@link BaseMessagingService}
     *
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Method for setting title of the push notifications.
     * Title is assigned in @{@link BaseMessagingService}
     */
    public void setTitle(String title){
        this.title = title;
    }

    /**
     * Method for obtaining message of the push notifications.
     * Message is assigned in @{@link BaseMessagingService}
     *
     * @return message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Method for setting message of the push notifications.
     * Message is assigned in @{@link BaseMessagingService}
     */
    public void setMessage(String message){
        this.message = message;
    }

    /**
     * Method for obtaining message of the push notifications.
     * BigMessage is assigned in @{@link BaseMessagingService}
     *
     * @return bigMessage
     */
    public String getBigMessage() {
        return bigMessage;
    }

    /**
     * Method for obtaining deeplink parameters of the push notifications.
     * Params are obtained from @{@link BaseMessagingService}
     *
     * @return params
     */
    public Map<String, String> getParams() {
        return params;
    }

    /**
     * Method for seting deeplink parameters of the push notifications.
     * Params are obtained from @{@link BaseMessagingService}
     *
     */
    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    /**
     * Method responsible for returning enitre unparsed deeplink
     *
     * @return application
     */
    public String getLink(){
        return link;
    }

    /**
     * Method responsible for seting enitre unparsed deeplink
     *
     */
    public void setLink(String link){
        this.link = link;
    }

    /**
     * Method responsible for returning notification id
     ** @return messageId
     */
    public int getMessageId() {
        return messageId;
    }

    /**
     * Method responsible for seting notification id
     *
     */
    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    /**
     * Method responsible for returning application context
     *
     * @return application
     */
    protected Application getContext() {
        return application;
    }


    /**
     * Checks if notification data is valid.
     * @return boolean
     */
    public boolean isDataValid(){
        return !TextUtils.isEmpty(title);
    }

    /**
     * Method responsible for obtaining remote message
     *
     * @return RemoteMessage
     */
    public RemoteMessage getRemoteMessage() {
        return remoteMessage;
    }


    /**
     * Method responsible for setting remote message
     *
     */
    public void setRemoteMessage(RemoteMessage remoteMessage) {
        this.remoteMessage = remoteMessage;
    }
}
