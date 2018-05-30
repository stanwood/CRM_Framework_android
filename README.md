# CRM Framework (Android)

[![](https://jitpack.io/v/stanwood/CRM_Framework_android.svg)](https://jitpack.io/#stanwood/CRM_Framework_android)

This library contains easy to implement support for firebase push notifications. It uses Analytics Framework (https://github.com/stanwood/Analytics_Framework_android) to track application data which can be used for generating CRM campaigns. 

## Motivation
The motivation of this framework is to ease the implementation of push notification implementation. The notifications and inapp messages are triggered based on firebase data tracked by analytics framework. This data can be used to create funnels and creating CRM campaigns.

## Requirements
- Integrated Analytics Framework
- Sample app needs to integrate firebase. Please remember to add google-services.json to the project. 

## Import
//TODO

## Integration

1. Init CRM class in your application's `onCreate()` method and pass to it application context and instance of BaseAnalyticsTracker.
CRM class is used for passing the push token to the tracking library.
```java
  Crm.init(this, SimpleAppTracker.instance());
```

2. Create a concrete implementation of `BaseNotificationHandler` and override `createNotification()`. 
`createNotification()` is called after receiving the push notification and is responsible for handling its display.
```java
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
```

3. Create a concrete implementation of `BaseMessagingService` override `createBaseNotificationHandler()` and return a new instance of Notification Handler from step 2. 
```java
    @NonNull
    @Override
    public BaseNotificationHandler createBaseNotificationHandler() {
        return new CrmNotificationsHandler(getApplication(), SimpleAppTracker.instance());
    }
```

4. Register the service in your application manifest.
```java
 <service android:name=".notification.CrmNotificationService">
            <intent-filter>
                <action android:name="com.google.firebase.MESSAGING_EVENT" />
            </intent-filter>
 </service>
```

5. In App messages exemplar implementation. InApp messages are received as a silent push notifications and stored for later use. 
On start of every screen `displayInAppIfAny()` is checking if there are any pending messages for specific screen to be displayed. 
```java
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
            //Sample implementation of analytics framework needed for creating funnel based CRM 
            //campaigns in order to increase re-targeting. 
            SimpleAppTracker.instance().trackScreenView(getScreenName());
        }
    }
}
```

6. (Optional) create crm.xml file with custom specifications
```xml
<?xml version="1.0" encoding="utf-8"?>
<resources xmlns:tools="http://schemas.android.com/tools" tools:override="true">
    <!--Default channel id -->
    <string name="default_notification_channel_id">chanel_id</string>
    <!--Default channel description -->
    <string name="default_notification_channel_description">Notifications Description</string>
    <!--Deeplink key used for parsing data send from push server -->
    <string name="deeplink_key">link</string>
    <!--Title key used for parsing data send from push server -->
    <string name="title_key">title</string>
    <!--Message key used for parsing data send from push server -->
    <string name="message_key">message</string>
    <!--Big message key used for parsing data send from push server -->
    <string name="big_message_key">messageBig</string>
    <!--Message Id key used for parsing data send from push server. Used for grouping/replacing notifications based on campaign -->
    <string name="messageId_key">messageId</string>
    <!--Default notification color -->
    <color name="default_notification_color">#FF4081</color>
    <!--Message type key for differentiating  push notifications from inApp messages -->
    <string name="message_type_key">type</string>
    <!--Target Screen on which the inApp message should be displayed -->
    <string name="target_screen">target_screen</string>
</resources>
```

7. (Optional) Specifying different designs for inApps messages based on the screen name in your application's `onCreate()` method:
```java
    Crm.getInstance().addDialogDesign("main_view", CustomInAppDialogFragment.class);
```

Alternatively extended method can be used in order to display a custom InAppDialogFragment implementation.
```java
 Crm.getInstance().displayInAppIfAny(this, getScreenName(), new CustomInAppDialogFragment());
```

## Campaign tool (under development)

Campaign tool can be found:
- https://stanwood-crm.appspot.com/campaigns - very dummy implementation
- stub for firing event with debug information: https://stanwood-crm.appspot.com/event?user_email=zyzniewski@gmail.com - basically any GET params are injected into `user_properties`

